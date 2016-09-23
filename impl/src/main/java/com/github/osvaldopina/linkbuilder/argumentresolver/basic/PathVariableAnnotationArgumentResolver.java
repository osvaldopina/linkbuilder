package com.github.osvaldopina.linkbuilder.argumentresolver.basic;

import com.damnhandy.uri.template.UriTemplate;
import com.github.osvaldopina.linkbuilder.argumentresolver.ArgumentResolver;
import com.github.osvaldopina.linkbuilder.argumentresolver.variablesubstitutioncontroller.VariableSubstitutionController;
import com.github.osvaldopina.linkbuilder.utils.UriTemplateAugmenter;
import org.springframework.core.MethodParameter;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;


public class PathVariableAnnotationArgumentResolver implements ArgumentResolver {

    @Override
    public boolean resolveFor(MethodParameter methodParameter) {
        return methodParameter.hasParameterAnnotation(PathVariable.class);
    }

    @Override
    public void augmentTemplate(UriTemplateAugmenter uriTemplateAugmenter, MethodParameter methodParameter) {
    }


    public void setTemplateVariables(UriTemplate template, MethodParameter methodParameter, Object parameter,
                              VariableSubstitutionController variableSubstitutionController) {

        Assert.notNull(template);
        Assert.notNull(methodParameter);

        PathVariable pathVariable = methodParameter.getMethodAnnotation(PathVariable.class);


        if (variableSubstitutionController.substitute(methodParameter, pathVariable.value(), parameter)) {
            template.set(pathVariable.value(), parameter);
        }

    }
}
