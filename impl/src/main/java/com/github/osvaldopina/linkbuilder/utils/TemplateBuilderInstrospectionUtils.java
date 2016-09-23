package com.github.osvaldopina.linkbuilder.utils;

import com.github.osvaldopina.linkbuilder.LinkBuilderException;
import com.github.osvaldopina.linkbuilder.annotation.EnableSelfFromCurrentCall;
import com.github.osvaldopina.linkbuilder.annotation.GenerateUriTemplateFor;
import com.github.osvaldopina.linkbuilder.annotation.LinkTarget;
import com.github.osvaldopina.linkbuilder.annotation.Links;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.web.bind.annotation.RestController;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class TemplateBuilderInstrospectionUtils {

    public boolean haveToGenerateTemplateFor(Method method) {
        return !method.getDeclaringClass().getPackage().getName().startsWith("org.springframework") &&
                (AnnotationUtils.findAnnotation(method, GenerateUriTemplateFor.class) != null ||
                        AnnotationUtils.findAnnotation(method.getDeclaringClass(), GenerateUriTemplateFor.class) != null ||
                        AnnotationUtils.findAnnotation(method, LinkTarget.class) != null);
    }

    public boolean isRestController(Object bean) {
        return AnnotationUtils.findAnnotation(bean.getClass(), RestController.class) != null;
    }


    public List<Method> getAnnotatedMethods(Object bean, Class<? extends Annotation> annotationType) {
        List<Method> linkAnnotatedMethods = new ArrayList<Method>();
        for (Method method : bean.getClass().getMethods()) {
            if (AnnotationUtils.findAnnotation(method, annotationType) != null) {
                checkReturnResource(method);
                linkAnnotatedMethods.add(method);
            }
        }
        return Collections.unmodifiableList(linkAnnotatedMethods);
    }

    public void checkReturnResource(Method method) {
        if (!ResourceSupport.class.isAssignableFrom(method.getReturnType())) {
            throw new LinkBuilderException("Method " + method + " is not valid for @Link because it do not return " +
                    Resource.class);
        }
    }

    public boolean hasLinkTargetAnnotation(Method method) {
        return (AnnotationUtils.findAnnotation(method, LinkTarget.class) != null);
    }

    public String getLinkTarget(Method method) {
        LinkTarget linkTarget = AnnotationUtils.findAnnotation(method, LinkTarget.class);

        if (linkTarget.value() == null || "".equals(linkTarget.value())) {
            throw new LinkBuilderException("@LinkTarget for " + method + " must not be null of empty string!");
        }

        return (method.getDeclaringClass().getName() + ":" + linkTarget.value());
    }
}
