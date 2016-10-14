package com.github.osvaldopina.linkbuilder.utils.impl;

import com.github.osvaldopina.linkbuilder.LinkBuilderException;
import com.github.osvaldopina.linkbuilder.annotation.*;
import com.github.osvaldopina.linkbuilder.utils.IntrospectionUtils;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;

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
                (AnnotationUtils.findAnnotation(method, GenerateUriTemplateFor.class) != null ||
                        AnnotationUtils.findAnnotation(method.getDeclaringClass(), GenerateUriTemplateFor.class) != null ||
                        AnnotationUtils.findAnnotation(method, LinkTarget.class) != null);
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
    public Set<Method> getEnableSelfFromCurrentCallAnnotatedMethods(Object bean)  {
        return getAnnotatedMethods(bean, EnableSelfFromCurrentCall.class);
    }

    @Override
    public boolean isLinkTargetMethod(Method method) {
        return (AnnotationUtils.findAnnotation(method, LinkTarget.class) != null);
    }

    public String getLinkTarget(Method method) {
        LinkTarget linkTarget = AnnotationUtils.findAnnotation(method, LinkTarget.class);

        if (linkTarget.value() == null || "".equals(linkTarget.value())) {
            throw new LinkBuilderException("@LinkTarget for " + method + " must not be null of empty string!");
        }

        return (method.getDeclaringClass().getName() + ":" + linkTarget.value());
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
