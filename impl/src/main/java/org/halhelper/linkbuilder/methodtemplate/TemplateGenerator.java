package org.halhelper.linkbuilder.methodtemplate;

import com.damnhandy.uri.template.UriTemplate;
import org.halhelper.linkbuilder.argumentresolver.ArgumentResolver;
import org.halhelper.linkbuilder.argumentresolver.ArgumentResolvers;
import org.halhelper.linkbuilder.utils.UriTemplateAugmenter;
import org.springframework.core.MethodParameter;
import org.springframework.hateoas.core.MethodParameters;

import java.lang.reflect.Method;

/**
 * Created by osvaldopina on 1/4/16.
 */
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

            argumentResolver.augmentTemplate(uriTemplateAugmenter, methodParameter);
        }

        return uriTemplateAugmenter.getUriTemplate();

    }

    public static class MethodParametersFactory {
        MethodParameters create(Method method) {
            return new MethodParameters(method);
        }
    }

}
