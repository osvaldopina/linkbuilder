package com.github.osvaldopina.linkbuilder.argumentresolver;

import com.damnhandy.uri.template.UriTemplate;
import com.github.osvaldopina.linkbuilder.argumentresolver.variablesubstitutioncontroller.VariableSubstitutionController;
import com.github.osvaldopina.linkbuilder.utils.UriTemplateAugmenter;

import java.lang.reflect.Method;


public class EmptyArgumentResolver implements ArgumentResolver {

    @Override
    public boolean resolveFor(Method method, int parameterIndex) {
        return false;
    }

    @Override
    public void augmentTemplate(UriTemplateAugmenter uriTemplateAugmenter, Method method, int parameterIndex) {
    }


    public void setTemplateVariables(UriTemplate template, Method method, int parameterIndex, Object parameter,
                              VariableSubstitutionController variableSubstitutionController) {

    }

}
