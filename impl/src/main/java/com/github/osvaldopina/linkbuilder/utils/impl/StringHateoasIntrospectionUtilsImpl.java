package com.github.osvaldopina.linkbuilder.utils.impl;

import com.github.osvaldopina.linkbuilder.annotation.EnableSelfFromCurrentCall;
import com.github.osvaldopina.linkbuilder.annotation.GenerateUriTemplateFor;
import com.github.osvaldopina.linkbuilder.annotation.Links;
import com.github.osvaldopina.linkbuilder.utils.IntrospectionUtils;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class StringHateoasIntrospectionUtilsImpl implements IntrospectionUtils {

    @Override
    public boolean isPathVariableParameter(Method method, int parameterIndex) {
        MethodParameter methodParameter = new MethodParameter(method, parameterIndex);
        return methodParameter.hasParameterAnnotation(PathVariable.class);
    }

    @Override
    public String getPathVariableName(Method method, int parameterIndex) {
        MethodParameter methodParameter = new MethodParameter(method, parameterIndex);
        return methodParameter.getParameterAnnotation(PathVariable.class).value();
    }

    @Override
    public boolean isQueryVariableParameter(Method method, int parameterIndex) {
        MethodParameter methodParameter = new MethodParameter(method, parameterIndex);
        return methodParameter.hasParameterAnnotation(RequestParam.class);
    }

    @Override
    public String getQueryVariableName(Method method, int parameterIndex) {
        MethodParameter methodParameter = new MethodParameter(method, parameterIndex);
        return methodParameter.getParameterAnnotation(RequestParam.class).value();
    }

    @Override
    public boolean isRequestBodyVariableParameter(Method method, int parameterIndex) {
        MethodParameter methodParameter = new MethodParameter(method, parameterIndex);
        return methodParameter.hasParameterAnnotation(RequestBody.class);
    }

    @Override
    public boolean haveToGenerateTemplateFor(Method method) {
        return !method.getDeclaringClass().getPackage().getName().startsWith("org.springframework") &&
                (AnnotationUtils.findAnnotation(method, EnableSelfFromCurrentCall.class) != null ||
                        AnnotationUtils.findAnnotation(method.getDeclaringClass(), EnableSelfFromCurrentCall.class) != null ||
                        AnnotationUtils.findAnnotation(method, GenerateUriTemplateFor.class) != null ||
                        AnnotationUtils.findAnnotation(method.getDeclaringClass(), GenerateUriTemplateFor.class) != null);
    }

    @Override
    public boolean isRestController(Object bean) {
        return AnnotationUtils.findAnnotation(bean.getClass(), RestController.class) != null;
    }

    @Override
    public Set<Method> getLinksAnnotatedMethods(Object bean) {
        return getAnnotatedMethods(bean, Links.class);
    }

    @Override
    public Set<Method> getEnableSelfFromCurrentCallAnnotatedMethods(Object bean) {
        return getAnnotatedMethods(bean, EnableSelfFromCurrentCall.class);
    }

    @Override
    public String getMethodRel(Method method) {
        GenerateUriTemplateFor generateUriTemplateFor =
                AnnotationUtils.findAnnotation(method, GenerateUriTemplateFor.class);

        if (generateUriTemplateFor != null &&
                generateUriTemplateFor.rel() != null &&
                (!"" .equals(generateUriTemplateFor.rel().trim()))) {
            return generateUriTemplateFor.rel();
        } else {
            return null;
        }
    }


    private Set<Method> getAnnotatedMethods(Object bean, Class<? extends Annotation> annotationType) {
        Set<Method> annotatedMethods = new HashSet<Method>();
        for (Method method : bean.getClass().getMethods()) {
            if (AnnotationUtils.findAnnotation(method, annotationType) != null) {
                annotatedMethods.add(method);
            }
        }
        return Collections.unmodifiableSet(annotatedMethods);
    }


}
