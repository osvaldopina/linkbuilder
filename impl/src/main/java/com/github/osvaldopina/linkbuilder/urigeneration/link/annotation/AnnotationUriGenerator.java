package com.github.osvaldopina.linkbuilder.urigeneration.link.annotation;

import com.github.osvaldopina.linkbuilder.annotation.reader.properties.LinkAnnotationProperties;
import com.github.osvaldopina.linkbuilder.fromcall.MethodCall;

public interface AnnotationUriGenerator {

    String generateUri(LinkAnnotationProperties linkAnnotationProperties, MethodCall currentMethodCall, Object payload);

}
