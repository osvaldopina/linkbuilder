package com.github.osvaldopina.linkbuilder.urigeneration.link.annotation.impl;

import com.github.osvaldopina.linkbuilder.LinkBuilderException;
import com.github.osvaldopina.linkbuilder.urigeneration.link.annotation.AnnotatedMethodUriGenerator;
import com.github.osvaldopina.linkbuilder.urigeneration.link.annotation.AnnotatedMethodUriGenerators;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class AnnotatedMethodUriGeneratorsImpl implements AnnotatedMethodUriGenerators {

    private List<AnnotatedMethodUriGenerator> annotatedMethodUriGenerators =
            new ArrayList<AnnotatedMethodUriGenerator>();

    public AnnotatedMethodUriGeneratorsImpl(List<AnnotatedMethodUriGenerator> annotatedMethodUriGenerators) {
        this.annotatedMethodUriGenerators.addAll(annotatedMethodUriGenerators);
    }

    public AnnotatedMethodUriGenerator getAnnotatedMethodUriGenerator(Method method) {
        for(AnnotatedMethodUriGenerator annotatedMethodUriGenerator:annotatedMethodUriGenerators) {
            if (annotatedMethodUriGenerator.isAnnotated(method)) {
                return annotatedMethodUriGenerator;
            }
        }
        throw new LinkBuilderException("Could not find a AnnotatedMethodUriGenerator for controller " + method.getName());
    }


}