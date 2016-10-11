package com.github.osvaldopina.linkbuilder.methodtemplate.uridiscover.impl;

import com.damnhandy.uri.template.UriTemplate;
import com.github.osvaldopina.linkbuilder.annotation.Param;
import com.github.osvaldopina.linkbuilder.argumentresolver.ArgumentResolver;
import com.github.osvaldopina.linkbuilder.argumentresolver.ArgumentResolvers;
import com.github.osvaldopina.linkbuilder.argumentresolver.variablesubstitutioncontroller.VariableSubstitutionController;
import com.github.osvaldopina.linkbuilder.argumentresolver.variablesubstitutioncontroller.impl.SubstituteAllVariableSubstitutionController;
import com.github.osvaldopina.linkbuilder.methodtemplate.LinkGenerator;
import com.github.osvaldopina.linkbuilder.methodtemplate.MethodCall;
import com.github.osvaldopina.linkbuilder.methodtemplate.UriTemplateMethodMappings;
import com.github.osvaldopina.linkbuilder.expression.ExpressionExecutor;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.MethodParameter;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.MethodParameters;

public class LinkGeneratorImpl implements LinkGenerator, ApplicationContextAware {

    private ApplicationContext applicationContext;

    private VariableSubstitutionController defaultVariableSubstitutionController
            = new SubstituteAllVariableSubstitutionController();


    @Override
    public Link generate(MethodCall methodCall, boolean templated, String rel) {
        return generate(methodCall, templated, rel, defaultVariableSubstitutionController);
    }

    @Override
    public Link generate(MethodCall methodCall, boolean templated, String rel,
                         VariableSubstitutionController variableSubstitutionController) {


        UriTemplateMethodMappings uriTemplateMethodMappings =
                applicationContext.getBean(UriTemplateMethodMappings.class);

        UriTemplate template = uriTemplateMethodMappings.createNewTemplateForMethod(methodCall.getMethod());

        ArgumentResolvers argumentResolvers = applicationContext.getBean(ArgumentResolvers.class);

        MethodParameters methodParameters = new MethodParameters(methodCall.getMethod());

        ArgumentResolver argumentResolver;
        for (MethodParameter methodParameter : methodParameters.getParameters()) {

            argumentResolver = argumentResolvers.getArgumentResolverFor(
                    methodParameter.getMethod(),
                    methodParameter.getParameterIndex()
            );

            argumentResolver.setTemplateVariables(template,
                    methodParameter.getMethod(),
                    methodParameter.getParameterIndex(),
                    methodCall.getParam(methodParameter.getParameterIndex()),
                    variableSubstitutionController);
        }

        return new Link(templated ? template.expandPartial() : template.expand(), rel);
    }

    public Link generate(com.github.osvaldopina.linkbuilder.annotation.Link linkAnnotation, Object payLoad,
                         Object[] params) {

        Class<?> destination = linkAnnotation.destination();
        String targetLink = linkAnnotation.target();

        UriTemplateMethodMappings uriTemplateMethodMappingsImpl =
                applicationContext.getBean(UriTemplateMethodMappings.class);

        ExpressionExecutor expressionExecutor = applicationContext.getBean(ExpressionExecutor.class);

        UriTemplate template = uriTemplateMethodMappingsImpl.createNewTemplateForLinkTarget(destination, targetLink);

        for (Param param : linkAnnotation.params()) {
            template.set(param.name(), expressionExecutor.getValue(param.value(), payLoad, params));
        }

        return new org.springframework.hateoas.Link(
                linkAnnotation.templated() ? template.expandPartial() : template.expand(),
                linkAnnotation.relation());

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }


}


