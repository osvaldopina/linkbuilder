package com.github.osvaldopina.linkbuilder.annotation.reader.impl;

import com.github.osvaldopina.linkbuilder.annotation.Links;
import com.github.osvaldopina.linkbuilder.annotation.Variable;
import com.github.osvaldopina.linkbuilder.annotation.reader.AnnotationReader;
import com.github.osvaldopina.linkbuilder.annotation.reader.core.DestinationExtractor;
import com.github.osvaldopina.linkbuilder.annotation.reader.core.LinkRelExtractor;
import com.github.osvaldopina.linkbuilder.annotation.reader.properties.LinkAnnotationVariable;
import com.github.osvaldopina.linkbuilder.annotation.reader.properties.LinkAnnotationProperties;
import com.github.osvaldopina.linkbuilder.utils.IntrospectionUtils;
import com.github.osvaldopina.linkbuilder.utils.ReflectionUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class LinkAnnotationReader implements AnnotationReader {

    private LinkRelExtractor linkRelExtractor = LinkRelExtractor.INSTANCE;

    private DestinationExtractor destinationExtractor = DestinationExtractor.INSTANCE;

    private ReflectionUtils reflectionUtils = ReflectionUtils.INSTANCE;

    private IntrospectionUtils introspectionUtils;

    public LinkAnnotationReader(IntrospectionUtils introspectionUtils) {
        this.introspectionUtils = introspectionUtils;
    }


    @Override
    public boolean canRead(Method method) {
        return introspectionUtils.hasComposedAnnotation(method, Links.class);

    }

    @Override
    public boolean canRead(Class<?> resourceType) {
        return introspectionUtils.hasComposedAnnotation(resourceType, Links.class);
    }

    @Override
    public List<LinkAnnotationProperties> read(Method method) {
        return readAnnotation(method);
    }

    @Override
    public List<LinkAnnotationProperties> read(Class<?> resourceType) {
        return readAnnotation(resourceType);
    }



    private List<LinkAnnotationProperties> readAnnotation(AnnotatedElement annotatedElement) {
        Annotation  linksAnnotation = introspectionUtils.getComposedAnnotation(annotatedElement, Links.class);

        List<LinkAnnotationProperties> linkAnnotationProperties = new ArrayList<LinkAnnotationProperties>();
        for(Annotation link :reflectionUtils.callMethod(Annotation[].class, linksAnnotation, "value")) {
            linkAnnotationProperties.add(readLinkAnnotion(link));
        }
        return linkAnnotationProperties;
    }



    private LinkAnnotationProperties readLinkAnnotion(Annotation linkAnnotation) {
        String destination = destinationExtractor.extract(linkAnnotation);
        String rel = linkRelExtractor.extract(linkAnnotation);
        boolean templated = reflectionUtils.callMethod(boolean.class,linkAnnotation, "templated");
        List<LinkAnnotationVariable> linkVariables = new ArrayList<LinkAnnotationVariable>();
        for(Variable linkVariable : reflectionUtils.callMethod(Variable[].class,linkAnnotation, "variables")) {
            linkVariables.add(new LinkAnnotationVariable(linkVariable.name(), linkVariable.value(), linkVariable.when()));
        }
        return new LinkAnnotationProperties(destination, rel, templated, linkVariables);

    }

}
