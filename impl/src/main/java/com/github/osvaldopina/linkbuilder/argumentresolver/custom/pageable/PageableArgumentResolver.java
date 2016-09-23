package com.github.osvaldopina.linkbuilder.argumentresolver.custom.pageable;

import com.damnhandy.uri.template.UriTemplate;
import com.github.osvaldopina.linkbuilder.argumentresolver.ArgumentResolver;
import com.github.osvaldopina.linkbuilder.argumentresolver.variablesubstitutioncontroller.VariableSubstitutionController;
import com.github.osvaldopina.linkbuilder.utils.UriTemplateAugmenter;
import org.springframework.context.annotation.Conditional;
import org.springframework.core.MethodParameter;
import org.springframework.data.domain.Pageable;
import org.springframework.util.Assert;

import java.lang.reflect.Method;
import java.util.List;


@Conditional(PageableClassIsPresent.class)
public class PageableArgumentResolver implements ArgumentResolver {

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
                                     VariableSubstitutionController variableSubstitutionController) {

        Assert.notNull(template);
        Assert.isInstanceOf(Pageable.class, parameter);

        Pageable pageable = (Pageable) parameter;

        if (variableSubstitutionController.substitute(methodParameter, "page", parameter)) {
            template.set("page", pageable.getPageNumber());
        }

        if (variableSubstitutionController.substitute(methodParameter, "size", parameter)) {
            template.set("size", pageable.getPageSize());
        }

        if (variableSubstitutionController.substitute(methodParameter, "sort", parameter)) {
            template.set("sort", pageable.getSort());
        }

    }

}
