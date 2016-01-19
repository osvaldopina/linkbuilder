package com.github.osvaldopina.linkbuilder.argumentresolver;

import com.damnhandy.uri.template.UriTemplate;
import com.github.osvaldopina.linkbuilder.utils.UriTemplateAugmenter;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


/**
 * Created by deinf.osvaldo on 23/12/2015.
 */
@Component
public class PathVariableAnnotationArgumentResolver implements  ArgumentResolver {

    @Override
    public boolean resolveFor(MethodParameter methodParameter) {
        return methodParameter.hasParameterAnnotation(PathVariable.class);
    }

    @Override
    public void augmentTemplate(UriTemplateAugmenter uriTemplateAugmenter, MethodParameter methodParameter) {
    }


    @Override
    public void setTemplateVariables(UriTemplate template, MethodParameter methodParameter, Object parameter,
                                     List<String> templatedParamNames) {
        Assert.notNull(template);
        Assert.notNull(methodParameter);

        PathVariable pathVariable = methodParameter.getParameterAnnotation(PathVariable.class);

        if (! templatedParamNames.contains(pathVariable.value())) {
            template.set(pathVariable.value(), parameter);
        }

    }
}
