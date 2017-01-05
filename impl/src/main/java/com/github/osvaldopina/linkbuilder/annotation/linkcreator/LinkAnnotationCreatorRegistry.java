package com.github.osvaldopina.linkbuilder.annotation.linkcreator;

import java.lang.reflect.Method;

import com.github.osvaldopina.linkbuilder.annotation.reader.properties.LinkAnnotationProperties;
import com.github.osvaldopina.linkbuilder.fromcall.MethodCall;

public interface LinkAnnotationCreatorRegistry {

    LinkAnnotationCreator get(Method method);

    LinkAnnotationCreator get(Object resource);

}

