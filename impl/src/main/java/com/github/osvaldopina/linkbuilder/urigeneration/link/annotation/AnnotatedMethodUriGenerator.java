package com.github.osvaldopina.linkbuilder.urigeneration.link.annotation;


import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;

public interface AnnotatedMethodUriGenerator {

    boolean isAnnotated(Method method);

    List<? extends Annotation> getLinksAnnotation(Method method);

    Annotation getSelfLinkAnnotaiton(Method method);

    String  generate(Method method, Annotation annotation, Object resource, Object[] params);

}
