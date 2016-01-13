package com.github.osvaldopina.linkbuilder.methodtemplate;

import com.github.osvaldopina.linkbuilder.annotation.GenerateUriTemplateFor;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.reflect.Method;

/**
 * Created by osvaldopina on 1/4/16.
 */
public class TemplateGeneratorIntrospector {

    public boolean haveToGenerateTemplateFor(Method method) {
        return !method.getDeclaringClass().getPackage().getName().startsWith("org.springframework") &&
                (AnnotationUtils.findAnnotation(method, GenerateUriTemplateFor.class) != null ||
                        AnnotationUtils.findAnnotation(method.getDeclaringClass(), GenerateUriTemplateFor.class) != null);
    }
}
