package com.github.osvaldopina.linkbuilder.methodtemplate.impl;

import com.github.osvaldopina.linkbuilder.annotation.GenerateUriTemplateFor;
import com.github.osvaldopina.linkbuilder.annotation.LinkTarget;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.reflect.Method;
// TODO merge with other Instrospector
class TemplateGeneratorIntrospector {

    public boolean haveToGenerateTemplateFor(Method method) {
        return !method.getDeclaringClass().getPackage().getName().startsWith("org.springframework") &&
                (AnnotationUtils.findAnnotation(method, GenerateUriTemplateFor.class) != null ||
                        AnnotationUtils.findAnnotation(method.getDeclaringClass(), GenerateUriTemplateFor.class) != null ||
                AnnotationUtils.findAnnotation(method, LinkTarget.class) != null);
    }
}
