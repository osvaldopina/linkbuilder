package com.github.osvaldopina.linkbuilder.argumentresolver.basic;

import com.damnhandy.uri.template.UriTemplate;
import com.github.osvaldopina.linkbuilder.LinkBuilderException;
import com.github.osvaldopina.linkbuilder.argumentresolver.ArgumentResolver;
import com.github.osvaldopina.linkbuilder.argumentresolver.variablesubstitutioncontroller.VariableSubstitutionController;
import com.github.osvaldopina.linkbuilder.utils.IntrospectionUtils;
import com.github.osvaldopina.linkbuilder.utils.UriTemplateAugmenter;
import org.springframework.core.MethodParameter;
import org.springframework.util.Assert;

import java.lang.reflect.Method;
import java.util.Arrays;


public class PathVariableAnnotationArgumentResolver implements ArgumentResolver {

    private IntrospectionUtils introspectionUtils;

    public PathVariableAnnotationArgumentResolver(IntrospectionUtils introspectionUtils) {
        this.introspectionUtils = introspectionUtils;
    }


    @Override
    public boolean resolveFor(Method method, int parameterIndex) {

        return introspectionUtils.isPathVariableParameter(method, parameterIndex);
    }

    @Override
    public void augmentTemplate(UriTemplateAugmenter uriTemplateAugmenter, Method method, int parameterIndex) {
    }


    public void setTemplateVariables(UriTemplate template, Method method, int parameterIndex, Object parameter,
                              VariableSubstitutionController variableSubstitutionController) {

        Assert.notNull(template);
        Assert.notNull(method);

        MethodParameter methodParameter = new MethodParameter(method, parameterIndex);
        String variableName = introspectionUtils.getPathVariableName(method, parameterIndex);


        if (variableSubstitutionController.substitute(method, parameterIndex, variableName, parameter)) {
            if (Arrays.asList(template.getVariables()).contains(variableName)) {
                if (parameter != null) {
                    template.set(variableName, parameter);
                }
            }
            else {
                throw new LinkBuilderException(
                        "Could not find variable [" + variableName + "] in uri template " + template.getTemplate()
                );
            }
        }

    }
}
