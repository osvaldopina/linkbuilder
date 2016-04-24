package com.github.osvaldopina.linkbuilder.argumentresolver;

import com.github.osvaldopina.linkbuilder.LinkBuilderException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ArgumentResolvers {


    @Autowired
    List<ArgumentResolver> argumentResolvers = new ArrayList<ArgumentResolver>();

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
        throw new LinkBuilderException("Could not find a argument resolver for parameter # " +
                methodParameter.getParameterIndex() + " type " + methodParameter.getParameterType() +
                " on method " + methodParameter.getMethod());
    }

}
