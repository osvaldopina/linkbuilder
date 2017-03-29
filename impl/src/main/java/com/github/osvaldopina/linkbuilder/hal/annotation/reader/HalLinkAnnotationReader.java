package com.github.osvaldopina.linkbuilder.hal.annotation.reader;

import com.github.osvaldopina.linkbuilder.annotation.reader.AnnotationReader;
import com.github.osvaldopina.linkbuilder.annotation.reader.core.DestinationExtractor;
import com.github.osvaldopina.linkbuilder.annotation.reader.core.LinkRelExtractor;
import com.github.osvaldopina.linkbuilder.annotation.reader.properties.LinkAnnotationVariable;
import com.github.osvaldopina.linkbuilder.annotation.reader.properties.LinkAnnotationProperties;
import com.github.osvaldopina.linkbuilder.hal.annotation.HalLinks;
import com.github.osvaldopina.linkbuilder.hal.annotation.Variable;
import com.github.osvaldopina.linkbuilder.hal.annotation.reader.properties.HalLinkAnnotationProperties;
import com.github.osvaldopina.linkbuilder.utils.ReflectionUtils;
import org.springframework.core.annotation.AnnotationUtils;


import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
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
        return AnnotationUtils.findAnnotation(method, HalLinks.class) != null;
//        return halLinkAnnotationRetriever.getLinksAnnotation(method.getDeclaredAnnotations()) != null;
    }

    @Override
    public boolean canRead(Class<?> resourceType) {
        return AnnotationUtils.findAnnotation(resourceType, HalLinks.class) != null;
//        return halLinkAnnotationRetriever.getLinksAnnotation(resourceType.getDeclaredAnnotations()) != null;
    }

    @Override
    public List<LinkAnnotationProperties> read(Method method) {
        return readHalLinkProperties(method);
    }

    @Override
    public List<LinkAnnotationProperties> read(Class<?> resourceType) {
        return readHalLinkProperties(resourceType);
    }

    private List<LinkAnnotationProperties> readHalLinkProperties(AnnotatedElement annotatedElement) {
        Annotation  linksAnnotation = halLinkAnnotationRetriever.getLinksAnnotation(annotatedElement);

        List<LinkAnnotationProperties> linkAnnotationProperties = new ArrayList<LinkAnnotationProperties>();
        for(Annotation link :reflectionUtils.callMethod(Annotation[].class, linksAnnotation, "value")) {
            linkAnnotationProperties.add(readHalLinkAnnotion(link));
        }
        return linkAnnotationProperties;

    }

    private HalLinkAnnotationProperties readHalLinkAnnotion(Annotation linkAnnotation) {
        String destination = destinationExtractor.extract(linkAnnotation);
        String rel = linkRelExtractor.extract(linkAnnotation);
        String when = reflectionUtils.callMethod(String.class, linkAnnotation, "when");
        when = when ==null||"".equals(when.trim())?null:when;
        boolean templated = reflectionUtils.callMethod(boolean.class,linkAnnotation, "templated");
        String hreflang  = reflectionUtils.callMethod(String.class,linkAnnotation, "hreflang");
        List<LinkAnnotationVariable> linkParameters = new ArrayList<LinkAnnotationVariable>();
        for(Variable linkVariable : reflectionUtils.callMethod(Variable[].class,linkAnnotation, "variables")) {
            linkParameters.add(new LinkAnnotationVariable(linkVariable.name(), linkVariable.value(), linkVariable.when()));
        }
        return new HalLinkAnnotationProperties(destination , rel,when, templated, hreflang, linkParameters);

    }

}
