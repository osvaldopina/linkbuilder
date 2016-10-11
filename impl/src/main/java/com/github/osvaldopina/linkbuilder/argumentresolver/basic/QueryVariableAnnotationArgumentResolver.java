package com.github.osvaldopina.linkbuilder.argumentresolver.basic;

import com.damnhandy.uri.template.UriTemplate;
import com.github.osvaldopina.linkbuilder.LinkBuilderException;
import com.github.osvaldopina.linkbuilder.argumentresolver.ArgumentResolver;
import com.github.osvaldopina.linkbuilder.argumentresolver.variablesubstitutioncontroller.VariableSubstitutionController;
import com.github.osvaldopina.linkbuilder.utils.IntrospectionUtils;
import com.github.osvaldopina.linkbuilder.utils.UriTemplateAugmenter;
import org.springframework.util.Assert;

import java.lang.reflect.Method;
import java.util.Arrays;


public class QueryVariableAnnotationArgumentResolver implements ArgumentResolver {

    private IntrospectionUtils introspectionUtils;

    public QueryVariableAnnotationArgumentResolver(IntrospectionUtils introspectionUtils) {
        this.introspectionUtils = introspectionUtils;
    }

    @Override
    public boolean resolveFor(Method method, int parameterIndex) {

        return introspectionUtils.isQueryVariableParameter(method, parameterIndex);
    }

    @Override
    public void augmentTemplate(UriTemplateAugmenter uriTemplateAugmenter, Method method, int parameterIndex) {
        Assert.notNull(uriTemplateAugmenter);
        Assert.notNull(method);

        uriTemplateAugmenter.addToQuery(introspectionUtils.getQueryVariableName(method, parameterIndex));

    }

    @Override
    public void setTemplateVariables(UriTemplate template, Method method, int parameterIndex, Object parameter,
                                     VariableSubstitutionController variableSubstitutionController) {

        Assert.notNull(template);
        Assert.notNull(method);

        String variableName = introspectionUtils.getQueryVariableName(method, parameterIndex);

        if (variableSubstitutionController.substitute(method, 0, variableName, parameter)) {
            if (Arrays.binarySearch(template.getVariables(), variableName) != -1) {
                template.set(variableName, parameter);
            }
            else {
                throw new LinkBuilderException(
                        "Could not find variable [" + variableName + "] in uri template " + template.getTemplate()
                );
            }
        }

    }


}
