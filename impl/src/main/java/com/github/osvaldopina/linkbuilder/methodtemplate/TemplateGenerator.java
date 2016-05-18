package com.github.osvaldopina.linkbuilder.methodtemplate;

import com.damnhandy.uri.template.UriTemplate;
import com.github.osvaldopina.linkbuilder.LinkBuilderException;
import com.github.osvaldopina.linkbuilder.argumentresolver.ArgumentResolver;
import com.github.osvaldopina.linkbuilder.argumentresolver.ArgumentResolvers;
import com.github.osvaldopina.linkbuilder.utils.UriTemplateAugmenter;
import org.springframework.core.MethodParameter;
import org.springframework.hateoas.core.MethodParameters;

import java.lang.reflect.Method;

public class TemplateGenerator {

    private TemplatePathDiscover templatePathDiscover = new TemplatePathDiscover();

    private MethodParametersFactory methodParametersFactory = new MethodParametersFactory();

    private UriTemplateAugmenter.Factory uriTemplateAugmenterFactory = new UriTemplateAugmenter.Factory();

    public UriTemplate generate(Method method, ArgumentResolvers argumentResolvers) {

        UriTemplateAugmenter uriTemplateAugmenter = uriTemplateAugmenterFactory.create();
        templatePathDiscover.augmentPath(uriTemplateAugmenter, method);

        MethodParameters methodParameters = methodParametersFactory.create(method);


        ArgumentResolver argumentResolver;
        for(MethodParameter methodParameter:methodParameters.getParameters()) {
            argumentResolver = argumentResolvers.getArgumentResolverFor(methodParameter);
            if (argumentResolver != null) {
                argumentResolver.augmentTemplate(uriTemplateAugmenter, methodParameter);
            }
        }

        return uriTemplateAugmenter.getUriTemplate();

    }

    public static class MethodParametersFactory {
        MethodParameters create(Method method) {
            return new MethodParameters(method);
        }
    }

}
