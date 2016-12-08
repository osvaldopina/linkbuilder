package com.github.osvaldopina.linkbuilder.linkcreator.annotation;

import com.github.osvaldopina.linkbuilder.annotation.reader.properties.LinkAnnotationProperties;
import com.github.osvaldopina.linkbuilder.fromcall.MethodCall;

public interface LinkAnnotationCreatorRegistry {

    LinkAnnotationCreator get(LinkAnnotationProperties linkAnnotationProperties, Object payload);

    LinkAnnotationCreator get(MethodCall currentMethodCall, Object payload);

}

