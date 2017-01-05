package com.github.osvaldopina.linkbuilder.annotation.linkcreator;

import com.github.osvaldopina.linkbuilder.annotation.reader.properties.LinkAnnotationProperties;
import com.github.osvaldopina.linkbuilder.fromcall.MethodCall;
import org.springframework.hateoas.Link;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;

public interface LinkAnnotationCreator {

    boolean canCreate(Method method);

    void createAndSetForMethodAnnotations(MethodCall methodCall, Object payload);

    boolean canCreate(Object payload);

    void createAndSetForResourceAnnotations(MethodCall methodCall, Object payload);

}

