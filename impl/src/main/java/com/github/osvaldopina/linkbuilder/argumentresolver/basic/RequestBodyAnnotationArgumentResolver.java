package com.github.osvaldopina.linkbuilder.argumentresolver.basic;

import com.damnhandy.uri.template.UriTemplate;
import com.github.osvaldopina.linkbuilder.argumentresolver.ArgumentResolver;
import com.github.osvaldopina.linkbuilder.argumentresolver.variablesubstitutioncontroller.VariableSubstitutionController;
import com.github.osvaldopina.linkbuilder.utils.IntrospectionUtils;
import com.github.osvaldopina.linkbuilder.utils.UriTemplateAugmenter;

import java.lang.reflect.Method;


public class RequestBodyAnnotationArgumentResolver implements ArgumentResolver {

    private IntrospectionUtils introspectionUtils;

    public RequestBodyAnnotationArgumentResolver(IntrospectionUtils introspectionUtils) {
        this.introspectionUtils = introspectionUtils;
    }

    @Override
    public boolean resolveFor(Method method, int parameterIndex) {
        return introspectionUtils.isRequestBodyVariableParameter(method, parameterIndex);
    }

    @Override
    public void augmentTemplate(UriTemplateAugmenter uriTemplateAugmenter, Method method, int parameterIndex) {

    }

    @Override
    public void setTemplateVariables(UriTemplate template, Method method, int parameterIndex, Object parameter,
                                     VariableSubstitutionController variableSubstitutionController) {

    }


}
