package com.github.osvaldopina.linkbuilder;

import com.github.osvaldopina.linkbuilder.argumentresolver.variablesubstitutioncontroller.impl.*;
import com.github.osvaldopina.linkbuilder.expression.ExpressionExecutor;
import com.github.osvaldopina.linkbuilder.fromcall.controllercallrecorder.CallRecorder;
import com.github.osvaldopina.linkbuilder.fromcall.controllercallrecorder.ControllerProxy;
import com.github.osvaldopina.linkbuilder.fromcall.currentcallrecorder.CurrentCallLocator;
import com.github.osvaldopina.linkbuilder.linkcreator.LinkCreator;
import com.github.osvaldopina.linkbuilder.linkcreator.LinkCreators;
import com.github.osvaldopina.linkbuilder.methodtemplate.MethodCall;
import com.github.osvaldopina.linkbuilder.methodtemplate.urigenerator.MethodCallUriGenerator;
import org.springframework.util.Assert;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class BaseLinkBuilder implements LinkBuilder, CallRecorder {

    private String rel;
    private boolean fromCurrentCall;
    private LinksBuilder linksBuilder;
    private VariableSubstitutionControllers variableSubstitutionControllers = new VariableSubstitutionControllers();
    private Object payload;
    private MethodCallUriGenerator methodCallUriGenerator;
    private MethodCall methodCall;
    private ExpressionExecutor expressionExecutor;
    private CurrentCallLocator currentCallLocator;
    private LinkCreators linkCreators;

    private String expression;

    public BaseLinkBuilder(
            LinksBuilder linksBuilder,
            ExpressionExecutor expressionExecutor,
            MethodCallUriGenerator methodCallUriGenerator,
            CurrentCallLocator currentCallLocator,
            LinkCreators linkCreators,
            Object payload) {

        this.linksBuilder = linksBuilder;
        this.expressionExecutor = expressionExecutor;
        this.methodCallUriGenerator = methodCallUriGenerator;
        this.currentCallLocator = currentCallLocator;
        this.linkCreators = linkCreators;
        this.payload = payload;
    }

    @Override
    public LinkBuilder withSelfRel() {
        this.rel = "self";
        return this;
    }

    @Override
    public LinkBuilder withRel(String rel) {
        this.rel = rel;
        return this;
    }

    @Override
    public LinkBuilder setExpressionPayload(Object payload) {
        this.payload = payload;
        return this;
    }

    @Override
    public LinkBuilder when(String expression) {
        this.expression = expression;
        return this;
    }

    @Override
    public LinkBuilder fromCurrentCall() {
        this.fromCurrentCall = true;
        return this;
    }

    @Override
    public LinkBuilder variableAsTemplate(int paramIndex) {
        variableSubstitutionControllers.addVariableSubstitutionControllerAggregator(
                new NotSubstituteParameterIndexVariableSubstitutionController(paramIndex)
        );
        return this;
    }

    @Override
    public LinkBuilder variableAsTemplate(String variableName) {
        variableSubstitutionControllers.addVariableSubstitutionControllerAggregator(
                new NotSubstituteVariableNameVariableSubstitutionController(variableName)
        );
        return this;
    }

    @Override
    public LinkBuilder nullVariablesAsTemplate() {
        variableSubstitutionControllers.addVariableSubstitutionControllerAggregator(
                new NotSubstituteNullValueVariableSubstitutionController()
        );
        return this;
    }

    @Override
    public LinkBuilder allParamsAsTemplate() {
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
    public LinkBuilder link() {
        return linksBuilder.link();
    }

    @Override
    public Object build() {
        if (fromCurrentCall) {
            methodCall = currentCallLocator.getCurrentCall();
        }

        String uri = methodCallUriGenerator.generate(methodCall, isTemplated(), variableSubstitutionControllers);

        LinkCreator linkCreator = linkCreators.get(this);

        return linkCreator.create(uri, this);

    }

    public void builAndSet() {
        if (fromCurrentCall) {
            methodCall = currentCallLocator.getCurrentCall();
        }

        String uri = methodCallUriGenerator.generate(methodCall, isTemplated(), variableSubstitutionControllers);

        LinkCreator linkCreator = linkCreators.get(this);

        linkCreator.createAndSet(uri, this, payload);

    }


    public boolean whenExpressionIsTrue() {
        if (expression != null) {
            return expressionExecutor.isTrue(expression, payload, null);
        } else {
            return true;
        }
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

    @Override
    public void record(MethodCall methodCall) {
        this.methodCall = methodCall;
    }

    protected String getRel() {
        return rel;
    }


}

