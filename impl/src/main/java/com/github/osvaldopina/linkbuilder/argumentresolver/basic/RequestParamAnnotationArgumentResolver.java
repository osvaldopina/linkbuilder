package com.github.osvaldopina.linkbuilder.argumentresolver.basic;

import com.damnhandy.uri.template.UriTemplate;
import com.github.osvaldopina.linkbuilder.argumentresolver.ArgumentResolver;
import com.github.osvaldopina.linkbuilder.argumentresolver.variablesubstitutioncontroller.VariableSubstitutionController;
import com.github.osvaldopina.linkbuilder.utils.UriTemplateAugmenter;
import org.springframework.core.MethodParameter;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestParam;


public class RequestParamAnnotationArgumentResolver implements ArgumentResolver {

    @Override
    public boolean resolveFor(MethodParameter methodParameter) {
        return methodParameter.hasParameterAnnotation(RequestParam.class);
    }

    @Override
    public void augmentTemplate(UriTemplateAugmenter uriTemplateAugmenter, MethodParameter methodParameter) {
        Assert.notNull(uriTemplateAugmenter);
        Assert.notNull(methodParameter);

        RequestParam requestParam = methodParameter.getParameterAnnotation(RequestParam.class);

        uriTemplateAugmenter.addToQuery(requestParam.value());

    }

    @Override
    public void setTemplateVariables(UriTemplate template, MethodParameter methodParameter, Object parameter,
                                     VariableSubstitutionController variableSubstitutionController) {

        Assert.notNull(template);
        Assert.notNull(methodParameter);

        RequestParam requestParam = methodParameter.getParameterAnnotation(RequestParam.class);

        if (variableSubstitutionController.substitute(methodParameter, requestParam.value(), parameter)) {
            template.set(requestParam.value(), parameter);
        }

    }


}
