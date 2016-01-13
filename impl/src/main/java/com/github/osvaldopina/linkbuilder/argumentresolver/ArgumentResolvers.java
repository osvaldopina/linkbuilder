package com.github.osvaldopina.linkbuilder.argumentresolver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by deinf.osvaldo on 23/12/2015.
 */
@Component
public class ArgumentResolvers {


    @Autowired
    List<ArgumentResolver> argumentResolvers = new ArrayList<ArgumentResolver>();

    public ArgumentResolvers(){

    }

    public ArgumentResolvers(List<ArgumentResolver> argumentResolvers) {
        this.argumentResolvers = argumentResolvers;
    }


    public ArgumentResolver getArgumentResolverFor(MethodParameter methodParameter) {
        for(ArgumentResolver argumentResolver:argumentResolvers) {
            if (argumentResolver.resolveFor(methodParameter)) {
                return argumentResolver;
            }
        }
        return null;
    }

}
