package com.github.osvaldopina.linkbuilder.argumentresolver;

import com.damnhandy.uri.template.UriTemplate;
import com.github.osvaldopina.linkbuilder.utils.UriTemplateAugmenter;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Component
public class RequestBodyAnnotationArgumentResolver implements  ArgumentResolver {

    @Override
    public boolean resolveFor(MethodParameter methodParameter) {
        return methodParameter.hasParameterAnnotation(RequestBody.class);
    }

    @Override
    public void augmentTemplate(UriTemplateAugmenter uriTemplateAugmenter, MethodParameter methodParameter) {
    }


    @Override
    public void setTemplateVariables(UriTemplate template, MethodParameter methodParameter, Object parameter,
                                     List<String> templatedParamNames) {
    }
}
