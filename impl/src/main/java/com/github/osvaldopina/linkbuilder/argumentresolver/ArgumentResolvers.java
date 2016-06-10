package com.github.osvaldopina.linkbuilder.argumentresolver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;

import java.util.ArrayList;
import java.util.List;

public class ArgumentResolvers {


    @Autowired
    List<ArgumentResolver> argumentResolvers = new ArrayList<ArgumentResolver>();

    private EmptyArgumentResolver emptyArgumentResolver = new EmptyArgumentResolver();

    protected ArgumentResolvers(List<ArgumentResolver> argumentResolvers) {
        this.argumentResolvers = argumentResolvers;
    }

    public ArgumentResolvers() {

    }


    public ArgumentResolver getArgumentResolverFor(MethodParameter methodParameter) {
        for(ArgumentResolver argumentResolver:argumentResolvers) {
            if (argumentResolver.resolveFor(methodParameter)) {
                return argumentResolver;
            }
        }
        return emptyArgumentResolver;
    }

}
