package com.github.osvaldopina.linkbuilder.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.Set;

public interface IntrospectionUtils {

    boolean isPathVariableParameter(Method method, int parameterIndex);

    String getPathVariableName(Method method, int parameterIndex);

    boolean isQueryVariableParameter(Method method, int parameterIndex);

    String getQueryVariableName(Method method, int parameterIndex);

    boolean isRequestBodyVariableParameter(Method method, int parameterIndex);

    boolean haveToGenerateTemplateFor(Method method);

    boolean isRestController(Object bean);

    Set<Method> getAnnotatedMethods(Object bean, Class<? extends Annotation> annotationType);

    boolean isEnableSelfFromCurrentCallMethod(Method method);

    String getMethodRel(Method method);

    String getMethodDestination(Method method);

    boolean hasComposedAnnotation(AnnotatedElement annotatedElement, Class<? extends Annotation> annotationType);


    <T extends Annotation> T getComposedAnnotation(AnnotatedElement annotatedElement, Class<T> annotationType);
}
