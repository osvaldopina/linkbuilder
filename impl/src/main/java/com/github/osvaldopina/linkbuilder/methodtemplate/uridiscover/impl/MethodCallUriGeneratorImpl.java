package com.github.osvaldopina.linkbuilder.methodtemplate.uridiscover.impl;

import com.damnhandy.uri.template.UriTemplate;
import com.github.osvaldopina.linkbuilder.argumentresolver.ArgumentResolver;
import com.github.osvaldopina.linkbuilder.argumentresolver.ArgumentResolvers;
import com.github.osvaldopina.linkbuilder.argumentresolver.variablesubstitutioncontroller.VariableSubstitutionController;
import com.github.osvaldopina.linkbuilder.argumentresolver.variablesubstitutioncontroller.impl.SubstituteAllVariableSubstitutionController;
import com.github.osvaldopina.linkbuilder.methodtemplate.urigenerator.MethodCallUriGenerator;
import com.github.osvaldopina.linkbuilder.methodtemplate.MethodCall;
import com.github.osvaldopina.linkbuilder.methodtemplate.UriTemplateMethodMappings;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.MethodParameter;
import org.springframework.hateoas.core.MethodParameters;

public class MethodCallUriGeneratorImpl implements MethodCallUriGenerator, ApplicationContextAware {

    private ApplicationContext applicationContext;

    private VariableSubstitutionController defaultVariableSubstitutionController
            = new SubstituteAllVariableSubstitutionController();


    @Override
    public String generate(MethodCall methodCall, boolean templated) {
        return generate(methodCall, templated, defaultVariableSubstitutionController);
    }

    @Override
    public String generate(MethodCall methodCall, boolean templated,
                         VariableSubstitutionController variableSubstitutionController) {

        StringBuffer log = new StringBuffer();

        UriTemplateMethodMappings uriTemplateMethodMappings =
                applicationContext.getBean(UriTemplateMethodMappings.class);

        UriTemplate template = uriTemplateMethodMappings.createTemplateForMethod(methodCall.getMethod());
        log.append("template: ");
        log.append(template.getTemplate());

        ArgumentResolvers argumentResolvers = applicationContext.getBean(ArgumentResolvers.class);

        MethodParameters methodParameters = new MethodParameters(methodCall.getMethod());

        ArgumentResolver argumentResolver;
        for (MethodParameter methodParameter : methodParameters.getParameters()) {

            argumentResolver = argumentResolvers.getArgumentResolverFor(
                    methodParameter.getMethod(),
                    methodParameter.getParameterIndex()
            );

            log.append(" method: ");
            log.append(methodParameter.getMethod());

            log.append(" parameterIndex: ");
            log.append(methodParameter.getParameterIndex());

            log.append(" parameterValue: ");
            log.append(methodCall.getParam(methodParameter.getParameterIndex()));


            argumentResolver.setTemplateVariables(template,
                    methodParameter.getMethod(),
                    methodParameter.getParameterIndex(),
                    methodCall.getParam(methodParameter.getParameterIndex()),
                    variableSubstitutionController);
        }


        System.out.println("########$$$$$$$$ LOG " + log);

        return templated ? template.expandPartial() : template.expand();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }


}


