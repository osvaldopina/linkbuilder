package com.github.osvaldopina.linkbuilder.utils.impl;

import com.github.osvaldopina.linkbuilder.LinkBuilderException;
import com.github.osvaldopina.linkbuilder.annotation.SelfFromCurrentCall;
import com.github.osvaldopina.linkbuilder.annotation.GenerateUriTemplateFor;
import com.github.osvaldopina.linkbuilder.utils.IntrospectionUtils;
import com.github.osvaldopina.linkbuilder.utils.ReflectionUtils;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class StringHateoasIntrospectionUtilsImpl implements IntrospectionUtils {

    private ReflectionUtils reflectionUtils = ReflectionUtils.INSTANCE;


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
                (AnnotationUtils.findAnnotation(method, SelfFromCurrentCall.class) != null ||
                        AnnotationUtils.findAnnotation(method, GenerateUriTemplateFor.class) != null);
    }

    @Override
    public boolean isRestController(Object bean) {
        return AnnotationUtils.findAnnotation(bean.getClass(), RestController.class) != null;
    }


    @Override
    public Set<Method> getAnnotatedMethods(Object bean, Class<? extends Annotation> annotationType) {
        Set<Method> annotatedMethods = new HashSet<Method>();
        for (Method method : bean.getClass().getMethods()) {
            if (AnnotationUtils.findAnnotation(method, annotationType) != null) {
                annotatedMethods.add(method);
            }
        }
        return Collections.unmodifiableSet(annotatedMethods);
    }

    @Override
    public boolean isEnableSelfFromCurrentCallMethod(Method method) {
        return AnnotationUtils.findAnnotation(method, SelfFromCurrentCall.class) != null;
    }

    @Override
    public String getMethodRel(Method method) {
        GenerateUriTemplateFor generateUriTemplateFor =
                AnnotationUtils.findAnnotation(method, GenerateUriTemplateFor.class);

        if (generateUriTemplateFor != null &&
                generateUriTemplateFor.rel() != null &&
                (!"".equals(generateUriTemplateFor.rel().trim()))) {
            return generateUriTemplateFor.rel();
        } else {
            return null;
        }
    }

    @Override
        public String getMethodDestination(Method method) {
        GenerateUriTemplateFor generateUriTemplateFor = method.getAnnotation(GenerateUriTemplateFor.class);

        if (generateUriTemplateFor != null) {
            return generateUriTemplateFor.destination();
        }

        for (Annotation annotation : method.getAnnotations()) {
            if (annotation.annotationType().getAnnotation(GenerateUriTemplateFor.class) != null) {
                if (reflectionUtils.hasMethod(annotation, "destination")) {
                    Object destination = reflectionUtils.callMethod(Object.class, annotation, "destination");
                    if (destination != null) {
                        return destination.toString();
                    }
                }
            }
        }
        return null;
    }

    @Override
    public boolean hasComposedAnnotation(AnnotatedElement annotatedElement, Class<? extends Annotation> annotationType) {
        return AnnotationUtils.findAnnotation(annotatedElement, annotationType) != null;
    }


    @Override
    public <T extends Annotation> T getComposedAnnotation(AnnotatedElement annotatedElement, Class<T> annotationType) {
        for (Annotation elementAnnotation : annotatedElement.getDeclaredAnnotations()) {
            if (elementAnnotation.annotationType().equals(annotationType) ||
                    elementAnnotation.annotationType().getAnnotation(annotationType) != null) {
                return (T) elementAnnotation;
            }
        }
        throw new LinkBuilderException("Could not find composed annotation " + annotationType + " in " + annotatedElement);
    }

}
