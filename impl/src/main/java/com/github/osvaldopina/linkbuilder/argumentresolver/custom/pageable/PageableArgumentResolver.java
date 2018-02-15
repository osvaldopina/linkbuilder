package com.github.osvaldopina.linkbuilder.argumentresolver.custom.pageable;

import com.damnhandy.uri.template.UriTemplate;
import com.github.osvaldopina.linkbuilder.argumentresolver.ArgumentResolver;
import com.github.osvaldopina.linkbuilder.argumentresolver.variablesubstitutioncontroller.VariableSubstitutionController;
import com.github.osvaldopina.linkbuilder.utils.UriTemplateAugmenter;
import org.springframework.context.annotation.Conditional;
import org.springframework.data.domain.Pageable;
import org.springframework.util.Assert;

import java.lang.reflect.Method;


@Conditional(PageableClassIsPresent.class)
public class PageableArgumentResolver implements ArgumentResolver {

    @Override
    public boolean resolveFor(Method method, int parameterIndex) {

        return Pageable.class.isAssignableFrom(method.getParameterTypes()[parameterIndex]);
    }

    @Override
    public void augmentTemplate(UriTemplateAugmenter uriTemplateAugmenter, Method method, int parameterIndex) {
        Assert.notNull(uriTemplateAugmenter);

        uriTemplateAugmenter.addToQuery("page");
        uriTemplateAugmenter.addToQuery("size");
        uriTemplateAugmenter.addToQuery("sort");
    }

    @Override
    public void setTemplateVariables(UriTemplate template, Method method, int parameterIndex, Object parameter,
                                     VariableSubstitutionController variableSubstitutionController) {

        Assert.notNull(template);
        Assert.isInstanceOf(Pageable.class, parameter);

        Pageable pageable = (Pageable) parameter;

        if (variableSubstitutionController.substitute(method, parameterIndex, "page", parameter)) {
            template.set("page", pageable.getPageNumber());
        }

        if (variableSubstitutionController.substitute(method, parameterIndex, "size", parameter)) {
            template.set("size", pageable.getPageSize());
        }

        if (variableSubstitutionController.substitute(method, parameterIndex, "sort", parameter)) {
            template.set("sort", pageable.getSort());
        }

    }

}
