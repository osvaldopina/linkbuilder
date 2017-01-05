package com.github.osvaldopina.linkbuilder.template.generation.argumentresolver;

import com.github.osvaldopina.linkbuilder.LinkBuilderException;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class ArgumentResolverRegistry {

    private List<ArgumentResolver> argumentResolvers;

    public ArgumentResolverRegistry(List<ArgumentResolver> argumentResolvers) {
        this.argumentResolvers = new ArrayList<ArgumentResolver>(argumentResolvers);
    }

    public ArgumentResolver getArgumentResolver(Method method, int parameterIndex) {
        for(ArgumentResolver argumentResolver:argumentResolvers) {
            if (argumentResolver.resolveFor(method, parameterIndex)) {
                return argumentResolver;
            }
        }
        throw new LinkBuilderException("Could not find ArgumentResolver for controller " + method +
                " and parameter index " + parameterIndex);
    }

}
