package com.github.osvaldopina.linkbuilder.hal.annotation.reader;

import com.github.osvaldopina.linkbuilder.annotation.Links;
import com.github.osvaldopina.linkbuilder.annotation.reader.AnnotationReader;
import com.github.osvaldopina.linkbuilder.annotation.reader.core.DestinationExtractor;
import com.github.osvaldopina.linkbuilder.annotation.reader.core.LinkRelExtractor;
import com.github.osvaldopina.linkbuilder.annotation.reader.properties.LinkAnnotationParameter;
import com.github.osvaldopina.linkbuilder.annotation.reader.properties.LinkAnnotationProperties;
import com.github.osvaldopina.linkbuilder.hal.annotation.HalLink;
import com.github.osvaldopina.linkbuilder.hal.annotation.HalLinks;
import com.github.osvaldopina.linkbuilder.hal.annotation.Param;
import com.github.osvaldopina.linkbuilder.hal.annotation.reader.properties.HalLinkAnnotationProperties;
import com.github.osvaldopina.linkbuilder.utils.ReflectionUtils;


import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class HalLinkAnnotationReader implements AnnotationReader {

    private LinkRelExtractor linkRelExtractor = LinkRelExtractor.INSTANCE;

    private DestinationExtractor destinationExtractor = DestinationExtractor.INSTANCE;

    private ReflectionUtils reflectionUtils = ReflectionUtils.INSTANCE;

    private HalLinkAnnotationRetriever halLinkAnnotationRetriever = HalLinkAnnotationRetriever.INSTANCE;

    @Override
    public boolean canRead(Method method) {
        return halLinkAnnotationRetriever.getLinksAnnotation(method.getDeclaredAnnotations()) != null;
    }

    @Override
    public boolean canRead(Class<?> payload) {
        return halLinkAnnotationRetriever.getLinksAnnotation(payload.getDeclaredAnnotations()) != null;
    }

    @Override
    public List<LinkAnnotationProperties> read(Method method) {
        return readHalLinkProperties(method.getDeclaredAnnotations());
    }

    @Override
    public List<LinkAnnotationProperties> read(Class<?> payloadType) {
        return readHalLinkProperties(payloadType.getDeclaredAnnotations());
    }

    private List<LinkAnnotationProperties> readHalLinkProperties(Annotation[] annotations) {
        Annotation  linksAnnotation = halLinkAnnotationRetriever.getLinksAnnotation(annotations);

        List<LinkAnnotationProperties> linkAnnotationProperties = new ArrayList<LinkAnnotationProperties>();
        for(Annotation link :reflectionUtils.callMethod(Annotation[].class, linksAnnotation, "value")) {
            linkAnnotationProperties.add(readHalLinkAnnotion(link));
        }
        return linkAnnotationProperties;

    }

    private HalLinkAnnotationProperties readHalLinkAnnotion(Annotation linkAnnotation) {
        String destination = destinationExtractor.extract(linkAnnotation);
        String rel = linkRelExtractor.extract(linkAnnotation);
        boolean templated = reflectionUtils.callMethod(boolean.class,linkAnnotation, "templated");
        String hreflang  = reflectionUtils.callMethod(String.class,linkAnnotation, "hreflang");
        List<LinkAnnotationParameter> linkParameters = new ArrayList<LinkAnnotationParameter>();
        for(Param linkParam: reflectionUtils.callMethod(Param[].class,linkAnnotation, "params")) {
            linkParameters.add(new LinkAnnotationParameter(linkParam.name(), linkParam.value(),linkParam.when()));
        }
        return new HalLinkAnnotationProperties(destination , rel, templated, hreflang, linkParameters);

    }

}
