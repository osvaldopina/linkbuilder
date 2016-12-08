package com.github.osvaldopina.linkbuilder.utils;

import java.lang.annotation.Annotation;
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

    Set<Method> getEnableSelfFromCurrentCallAnnotatedMethods(Object bean);

    boolean isEnableSelfFromCurrentCallMethod(Method method);

    String getMethodRel(Method method);

    String getMethodDestination(Method method);

  }
