package com.github.osvaldopina.linkbuilder.hal.annotation.linkcreator;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.osvaldopina.linkbuilder.annotation.reader.AnnotationReader;
import com.github.osvaldopina.linkbuilder.annotation.reader.AnnotationReaderCache;
import com.github.osvaldopina.linkbuilder.annotation.reader.properties.LinkAnnotationProperties;
import com.github.osvaldopina.linkbuilder.fromcall.MethodCall;
import com.github.osvaldopina.linkbuilder.hal.annotation.HalLinks;
import com.github.osvaldopina.linkbuilder.hal.annotation.linkcreator.embedded.valuesdiscover.EmbeddedValuesDiscover;
import com.github.osvaldopina.linkbuilder.hal.annotation.reader.HalLinkAnnotationReader;
import com.github.osvaldopina.linkbuilder.hal.annotation.reader.properties.HalLinkAnnotationProperties;
import com.github.osvaldopina.linkbuilder.hal.springhateoas.HalLink;
import com.github.osvaldopina.linkbuilder.annotation.linkcreator.LinkAnnotationCreator;
import com.github.osvaldopina.linkbuilder.urigeneration.base.BaseUriDiscover;
import com.github.osvaldopina.linkbuilder.urigeneration.link.annotation.AnnotationUriGenerator;
import com.github.osvaldopina.linkbuilder.urigeneration.link.methodcall.MethodCallUriGenerator;
import com.github.osvaldopina.linkbuilder.utils.IntrospectionUtils;
import com.github.osvaldopina.linkbuilder.utils.UrlPathContatenator;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.http.ResponseEntity;

public class SpringHateoasHalLinkAnnotationCreator implements LinkAnnotationCreator {


    private AnnotationUriGenerator annotationUriGenerator;

    private IntrospectionUtils introspectionUtils;

    private MethodCallUriGenerator methodCallUriGenerator;

    private AnnotationReader annotationReader;

    private EmbeddedValuesDiscover embeddedValuesDiscover = EmbeddedValuesDiscover.INSTANCE;

    private ObjectMapper objectMapper;


    public SpringHateoasHalLinkAnnotationCreator(AnnotationUriGenerator annotationUriGenerator,
                                                 IntrospectionUtils introspectionUtils,
                                                 MethodCallUriGenerator methodCallUriGenerator,
                                                 AnnotationReader annotationReader,
                                                 ObjectMapper objectMapper) {

        this.annotationUriGenerator = annotationUriGenerator;
        this.introspectionUtils = introspectionUtils;
        this.methodCallUriGenerator = methodCallUriGenerator;
        this.annotationReader = annotationReader;
        this.objectMapper = objectMapper;

    }

    @Override
    public boolean canCreate(Method method) {
        return introspectionUtils.
                hasComposedAnnotation(method, HalLinks.class);
    }

    @Override
    public void createAndSetForMethodAnnotations(MethodCall methodCall, Object resource) {
        List<LinkAnnotationProperties> linksProperties = annotationReader.read(methodCall.getMethod());

        for (LinkAnnotationProperties linkProperties : linksProperties) {
            createAndSet(linkProperties, methodCall, resource);
        }

    }

    @Override
    public boolean canCreate(Object resource) {
        return introspectionUtils.
                hasComposedAnnotation(resource.getClass(), HalLinks.class);
    }

    @Override
    public void createAndSetForResourceAnnotations(MethodCall methodCall, Object resource) {
        if (resource != null && annotationReader.canRead(resource.getClass())) {
            List<LinkAnnotationProperties> linksProperties = annotationReader.read(resource.getClass());

            for (LinkAnnotationProperties linkProperties : linksProperties) {
                createAndSet(linkProperties, methodCall, resource);
            }
        }

        List<Object> embeddedResources = embeddedValuesDiscover.getEmbeddedValues(objectMapper, resource);

        for (Object embeddedResource : embeddedResources) {
            if (embeddedResource instanceof ResourceSupport) {
                createAndSetForResourceAnnotations(methodCall, embeddedResource);
            }
        }
    }

    private void createAndSet(LinkAnnotationProperties linkAnnotationProperties, MethodCall currentMethodCall, Object resource) {
        HalLinkAnnotationProperties halLinkAnnotationProperties = (HalLinkAnnotationProperties) linkAnnotationProperties;
        if (resource instanceof ResponseEntity) {
            resource = ((ResponseEntity<Object>) resource).getBody();
        }
        if (resource instanceof ResourceSupport) {
            String uri = annotationUriGenerator.generateUri(linkAnnotationProperties, currentMethodCall, resource);
            ((ResourceSupport) resource).add(
                    new HalLink(uri, linkAnnotationProperties.getRel(), halLinkAnnotationProperties.getHreflang()));
        }
    }
}
