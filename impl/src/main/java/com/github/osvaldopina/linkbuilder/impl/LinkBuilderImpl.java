package com.github.osvaldopina.linkbuilder.impl;

import com.github.osvaldopina.linkbuilder.LinkBuilder;
import com.github.osvaldopina.linkbuilder.LinkBuilderException;
import com.github.osvaldopina.linkbuilder.argumentresolver.variablesubstitutioncontroller.impl.*;
import com.github.osvaldopina.linkbuilder.fromcall.currentcallrecorder.CurrentCall;
import com.github.osvaldopina.linkbuilder.fromcall.controllercallrecorder.ControllerProxy;
import com.github.osvaldopina.linkbuilder.methodtemplate.LinkGenerator;
import com.github.osvaldopina.linkbuilder.methodtemplate.MethodCall;
import com.github.osvaldopina.linkbuilder.expression.ExpressionExecutor;
import org.springframework.context.ApplicationContext;
import org.springframework.hateoas.Link;
import org.springframework.util.Assert;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class LinkBuilderImpl implements LinkBuilder<Link> {

    private String rel;
    private boolean fromCurrentCall;
    private ApplicationContext applicationContext;
    private LinksBuilderImpl linksBuilderImpl;
    private VariableSubstitutionControllers variableSubstitutionControllers = new VariableSubstitutionControllers();
    private Object payload;
    private LinkGenerator linkGenerator;
    private MethodCall methodCall;

    private ExpressionExecutor expressionExecutor;

    private String expression;

    public LinkBuilderImpl(ApplicationContext applicationContext, LinksBuilderImpl linksBuilderImpl, Object payload) {
        this.applicationContext = applicationContext;
        this.linksBuilderImpl = linksBuilderImpl;
        this.payload = payload;
        init();
    }

    public void init() {
        expressionExecutor = applicationContext.getBean(ExpressionExecutor.class);
        linkGenerator = applicationContext.getBean(LinkGenerator.class);

    }

    public void setMethodCall(MethodCall methodCall) {
        this.methodCall = methodCall;
    }

    public MethodCall getMethodCall() {
        return methodCall;
    }


    @Override
    public LinkBuilder<Link> withSelfRel() {
        this.rel = "self";
        return this;
    }

    @Override
    public LinkBuilder<Link> withRel(String rel) {
        this.rel = rel;
        return this;
    }

    @Override
    public LinkBuilder<Link> setExpressionPayload(Object payload) {
        this.payload = payload;
        return this;
    }

    @Override
    public LinkBuilder<Link> when(String expression) {
        this.expression = expression;
        return this;
    }

    @Override
    public LinkBuilder<Link> fromCurrentCall() {
        this.fromCurrentCall = true;
        return this;
    }

    @Override
    public LinkBuilder<Link> variableAsTemplate(int paramIndex) {
        variableSubstitutionControllers.addVariableSubstitutionControllerAggregator(
                new NotSubstituteParameterIndexVariableSubstitutionController(paramIndex)
        );
        return this;
    }

    @Override
    public LinkBuilder<Link> variableAsTemplate(String variableName) {
        variableSubstitutionControllers.addVariableSubstitutionControllerAggregator(
                new NotSubstituteVariableNameVariableSubstitutionController(variableName)
        );
        return this;
    }

    @Override
    public LinkBuilder<Link> nullVariablesAsTemplate() {
        variableSubstitutionControllers.addVariableSubstitutionControllerAggregator(
                new NotSubstituteNullValueVariableSubstitutionController()
        );
        return this;
    }

    @Override
    public LinkBuilder<Link> allParamsAsTemplate() {
        variableSubstitutionControllers.addVariableSubstitutionControllerAggregator(
                new SubstituteNoneVariableSubstitutionController()
        );
        return this;
    }

    @Override
    public <E> E fromControllerCall(Class<E> controllerClass) {
        return ControllerProxy.createProxy(controllerClass, this);
    }

    @Override
    public LinkBuilder<Link> link() {
        return linksBuilderImpl.link();
    }

    @Override
    public Link build() {
        if (fromCurrentCall) {
            CurrentCall currentCall = applicationContext.getBean(CurrentCall.class);
            methodCall = currentCall.getMethodCall();
        }

        return  linkGenerator.generate(methodCall, isTemplated(), rel, variableSubstitutionControllers);

    }

    public boolean whenExpressionIsTrue() {
        if (expression != null) {
            return expressionExecutor.isTrue(expression, payload, null);
        } else {
            return true;
        }

    }

    private void checkIfMethodCallIsPresent() {
        if (methodCall == null) {
            throw new LinkBuilderException("Could not determine which method call to get template!");
        }
    }

    @Override
    public List<Link> buildAll() {
        return linksBuilderImpl.buildAll();
    }

    private boolean isTemplated() {
        return variableSubstitutionControllers.hasVariableSubstitutionController();
    }

    private HttpServletRequest getCurrentRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        Assert.state(requestAttributes != null, "Could not find current request via RequestContextHolder");
        Assert.isInstanceOf(ServletRequestAttributes.class, requestAttributes);
        HttpServletRequest servletRequest = ((ServletRequestAttributes) requestAttributes).getRequest();
        Assert.state(servletRequest != null, "Could not find current HttpServletRequest");
        return servletRequest;
    }

}

