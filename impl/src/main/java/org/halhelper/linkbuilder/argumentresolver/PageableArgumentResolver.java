package org.halhelper.linkbuilder.argumentresolver;

import com.damnhandy.uri.template.UriTemplate;
import com.damnhandy.uri.template.UriTemplateBuilder;
import org.halhelper.linkbuilder.utils.UriTemplateAugmenter;
import org.springframework.core.MethodParameter;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.List;


/**
 * Created by deinf.osvaldo on 23/12/2015.
 */
@Component
public class PageableArgumentResolver implements  ArgumentResolver {

    @Override
    public boolean resolveFor(MethodParameter methodParameter) {
        return Pageable.class.isAssignableFrom(methodParameter.getParameterType());
    }

    @Override
    public void augmentTemplate(UriTemplateAugmenter uriTemplateAugmenter, MethodParameter methodParameter) {
        Assert.notNull(uriTemplateAugmenter);

        uriTemplateAugmenter.addToQuery("page");
        uriTemplateAugmenter.addToQuery("size");
        uriTemplateAugmenter.addToQuery("sort");
    }

    @Override
    public void setTemplateVariables(UriTemplate template, MethodParameter methodParameter, Object parameter,
                                     List<String> templatedParamNames) {
        Assert.notNull(template);
        Assert.isInstanceOf(Pageable.class, parameter);

        Pageable pageable = (Pageable) parameter;

        if (! templatedParamNames.contains("page")) {
            template.set("page", pageable.getPageNumber());
        }

        if (! templatedParamNames.contains("size")) {
            template.set("size", pageable.getPageSize());
        }

        if ( ! templatedParamNames.contains("sort")) {
            template.set("sort", pageable.getSort());
        }

    }
}
