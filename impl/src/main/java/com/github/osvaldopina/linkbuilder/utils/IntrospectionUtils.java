package com.github.osvaldopina.linkbuilder.utils;

import com.github.osvaldopina.linkbuilder.annotation.GenerateUriTemplateFor;
import com.github.osvaldopina.linkbuilder.annotation.LinkTarget;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.RestController;

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

    Set<Method> getLinksAnnotatedMethods(Object bean);

    Set<Method> getEnableSelfFromCurrentCallAnnotatedMethods(Object bean);

    boolean isLinkTargetMethod(Method method);

    String getLinkTarget(Method method);

    String getMethodRel(Method method);


}
