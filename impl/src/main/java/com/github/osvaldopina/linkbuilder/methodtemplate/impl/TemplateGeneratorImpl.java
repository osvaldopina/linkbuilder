package com.github.osvaldopina.linkbuilder.methodtemplate.impl;

import com.damnhandy.uri.template.UriTemplate;
import com.github.osvaldopina.linkbuilder.argumentresolver.ArgumentResolver;
import com.github.osvaldopina.linkbuilder.argumentresolver.ArgumentResolvers;
import com.github.osvaldopina.linkbuilder.methodtemplate.TemplateGenerator;
import com.github.osvaldopina.linkbuilder.utils.UriTemplateAugmenter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.hateoas.core.MethodParameters;

import java.lang.reflect.Method;

public class TemplateGeneratorImpl implements TemplateGenerator {

    private TemplatePathDiscover templatePathDiscover = new TemplatePathDiscover();

    private MethodParametersFactory methodParametersFactory = new MethodParametersFactory();

    private UriTemplateAugmenter.Factory uriTemplateAugmenterFactory = new UriTemplateAugmenter.Factory();

    @Autowired
    private ArgumentResolvers argumentResolvers;

    @Override
    public UriTemplate generate(Method method) {

        UriTemplateAugmenter uriTemplateAugmenter = uriTemplateAugmenterFactory.create();
        templatePathDiscover.augmentPath(uriTemplateAugmenter, method);

        MethodParameters methodParameters = methodParametersFactory.create(method);


        ArgumentResolver argumentResolver;
        for (MethodParameter methodParameter : methodParameters.getParameters()) {
            argumentResolver = argumentResolvers.getArgumentResolverFor(
                    methodParameter.getMethod(), methodParameter.getParameterIndex());

            argumentResolver.augmentTemplate(
                    uriTemplateAugmenter, methodParameter.getMethod(), methodParameter.getParameterIndex());
        }

        return uriTemplateAugmenter.getUriTemplate();

    }

    public static class MethodParametersFactory {
        MethodParameters create(Method method) {
            return new MethodParameters(method);
        }
    }

}
