package com.github.osvaldopina.linkbuilder.argumentresolver;

import com.damnhandy.uri.template.UriTemplate;
import com.github.osvaldopina.linkbuilder.utils.UriTemplateAugmenter;
import org.omg.CORBA.Request;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Component
public class RequestParamAnnotationArgumentResolver implements  ArgumentResolver {

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
                                     List<String> templatedParamNames) {
        Assert.notNull(template);
        Assert.notNull(methodParameter);

        RequestParam requestParam = methodParameter.getParameterAnnotation(RequestParam.class);

        if (! templatedParamNames.contains(requestParam.value())) {
            template.set(requestParam.value(), parameter);
        }

    }
}
