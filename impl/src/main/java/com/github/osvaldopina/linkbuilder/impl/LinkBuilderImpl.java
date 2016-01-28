package com.github.osvaldopina.linkbuilder.impl;

import com.damnhandy.uri.template.UriTemplate;
import com.github.osvaldopina.linkbuilder.LinkBuilder;
import com.github.osvaldopina.linkbuilder.LinkBuilderException;
import com.github.osvaldopina.linkbuilder.LinksBuilder;
import com.github.osvaldopina.linkbuilder.controllerproxy.CurrentCall;
import com.github.osvaldopina.linkbuilder.argumentresolver.ArgumentResolver;
import com.github.osvaldopina.linkbuilder.argumentresolver.ArgumentResolvers;
import com.github.osvaldopina.linkbuilder.controllerproxy.controllercall.ControllerProxy;
import com.github.osvaldopina.linkbuilder.methodtemplate.UriTemplateMethodMappings;
import org.springframework.context.ApplicationContext;
import org.springframework.core.MethodParameter;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.core.MethodParameters;
import org.springframework.util.Assert;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by deinf.osvaldo on 15/12/2015.
 */
public class LinkBuilderImpl implements LinkBuilder {



    private String rel;
    private boolean fromCurrentCall;
    private Method method;
    private Object[] parameters;
    private ApplicationContext applicationContext;
    private LinksBuilder linksBuilder;
    private List<Integer> templatedParamNumbers = new ArrayList<Integer>();
    private List<String> templatedParamNames = new ArrayList<String>();
    private boolean allParamsAsTemplate;

    private BaseUriDiscover baseUriDiscover = new BaseUriDiscover();


    public LinkBuilderImpl(ApplicationContext applicationContext, LinksBuilder linksBuilder) {
        this.applicationContext = applicationContext;
        this.linksBuilder = linksBuilder;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Method getMethod() {
        return method;
    }

    public void setParameters(Object[] parameters) {
        this.parameters = parameters;
    }

    public Object[] getParameters() {
        return parameters;
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
    public LinkBuilder fromCurrentCall() {
        this.fromCurrentCall = true;
        return this;
    }

    @Override
    public LinkBuilder paramAsTemplate(int paramNumber) {
        templatedParamNumbers.add(paramNumber);
        return this;
    }

    @Override
    public LinkBuilder paramAsTemplate(String templateParamName) {
        templatedParamNames.add(templateParamName);
        return this;
    }

    @Override
    public LinkBuilder allParamsAsTemplate() {
        allParamsAsTemplate = true;
        return this;
    }

    @Override
    public <T> T fromControllerCall(Class<T> controllerClass) {
        return ControllerProxy.createProxy(controllerClass, this);
    }

    @Override
    public LinkBuilder link() {
        return linksBuilder.link();
    }

    @Override
    public Link build() {
        if (fromCurrentCall) {
            CurrentCall currentCall = applicationContext.getBean(CurrentCall.class);
            method = currentCall.getMethod();
            parameters = currentCall.getParameters();
        }

        checkIfMethodIsPresent();

        UriTemplateMethodMappings uriTemplateMethodMappings =
                applicationContext.getBean(UriTemplateMethodMappings.class);


        UriTemplate template = uriTemplateMethodMappings.createNewTemplateForMethod(
                baseUriDiscover.getBaseUri(getCurrentRequest(), applicationContext),
                method);

        ArgumentResolvers argumentResolvers = applicationContext.getBean(ArgumentResolvers.class);

        MethodParameters methodParameters = new MethodParameters(method);



        if (!allParamsAsTemplate) {

            ArgumentResolver argumentResolver;
            for (MethodParameter methodParameter:methodParameters.getParameters()) {
                if (!templatedParamNumbers.contains(methodParameter.getParameterIndex())) {
                    checkIfParameterIsPresent(methodParameter.getParameterIndex());

                    argumentResolver = argumentResolvers.getArgumentResolverFor(methodParameter);
                    argumentResolver.setTemplateVariables(template,
                            methodParameter,
                            parameters[methodParameter.getParameterIndex()],
                            templatedParamNames);
                }
            }
        }

        return new Link(isTemplated() ? template.expandPartial() : template.expand(), rel);
    }


    private void checkIfMethodIsPresent() {
        if (method == null) {
            throw new LinkBuilderException("Could not determine which method to get template!");
        }
    }

    private void checkIfParameterIsPresent(int parameterIndex) {
        if (parameters == null || parameters.length < parameterIndex) {
            throw new LinkBuilderException("Tried to get parameter index " + parameterIndex + " for call on method " +
                    method.getName() + " but parameters " +
                    (parameters==null?" is null":"have length " + parameters.length));
        }

    }


    @Override
    public List<Link> buildAll() {
        return linksBuilder.buildAll();
    }

    private boolean isTemplated() {
        return allParamsAsTemplate || !templatedParamNames.isEmpty() || !templatedParamNumbers.isEmpty();
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
