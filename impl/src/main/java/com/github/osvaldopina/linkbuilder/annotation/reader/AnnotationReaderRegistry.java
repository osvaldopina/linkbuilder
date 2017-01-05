package com.github.osvaldopina.linkbuilder.annotation.reader;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public interface AnnotationReaderRegistry {

    AnnotationReader get(Method method);

    AnnotationReader get(Class<?> payload);


}
