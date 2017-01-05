package com.github.osvaldopina.linkbuilder.annotation.reader;

import com.github.osvaldopina.linkbuilder.annotation.reader.properties.LinkAnnotationProperties;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;

public interface AnnotationReader {

    boolean canRead(Method method);

    boolean canRead(Class<?> resourceType);

    List<LinkAnnotationProperties> read(Method method);

    List<LinkAnnotationProperties> read(Class<?> resourceType);

}
