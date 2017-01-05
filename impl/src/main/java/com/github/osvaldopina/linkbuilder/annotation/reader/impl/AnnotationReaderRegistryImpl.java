package com.github.osvaldopina.linkbuilder.annotation.reader.impl;

import com.github.osvaldopina.linkbuilder.LinkBuilderException;
import com.github.osvaldopina.linkbuilder.annotation.reader.AnnotationReader;
import com.github.osvaldopina.linkbuilder.annotation.reader.AnnotationReaderRegistry;
import com.github.osvaldopina.linkbuilder.annotation.reader.properties.LinkAnnotationProperties;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class AnnotationReaderRegistryImpl implements AnnotationReaderRegistry {

    private List<AnnotationReader> annotationReaders = new ArrayList<AnnotationReader>();


    public AnnotationReaderRegistryImpl(List<AnnotationReader> annotationReaders) {
        this.annotationReaders.addAll(annotationReaders);
    }


    @Override
    public AnnotationReader get(Method method) {
        for(AnnotationReader annotationReader:annotationReaders) {
            if (annotationReader.canRead(method)) {
                return annotationReader;
            }
        }
        return null;
    }

    @Override
    public AnnotationReader get(Class<?> payload) {
        for(AnnotationReader annotationReader:annotationReaders) {
            if (annotationReader.canRead(payload)) {
                return annotationReader;
            }
        }
        return null;
    }
}
