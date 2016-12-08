package com.github.osvaldopina.linkbuilder.linkcreator.annotation;

import com.github.osvaldopina.linkbuilder.annotation.reader.properties.LinkAnnotationProperties;
import com.github.osvaldopina.linkbuilder.fromcall.MethodCall;
import org.springframework.hateoas.Link;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;

public interface LinkAnnotationCreator {

    boolean canCreate(LinkAnnotationProperties linkAnnotationProperties, Object payload);

    boolean canCreate(MethodCall currentMethodCall, Object payload);

    void createAndSet(LinkAnnotationProperties linkAnnotationProperties, MethodCall currentMethodCall, Object payload);

    void createAndSetSelfLinkIfNeeded(MethodCall currentMethodCall, Object payload);


}

