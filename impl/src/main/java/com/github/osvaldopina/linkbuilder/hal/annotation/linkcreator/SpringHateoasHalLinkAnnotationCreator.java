package com.github.osvaldopina.linkbuilder.hal.annotation.linkcreator;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.osvaldopina.linkbuilder.LinkBuilderException;
import com.github.osvaldopina.linkbuilder.annotation.reader.AnnotationReader;
import com.github.osvaldopina.linkbuilder.annotation.reader.AnnotationReaderCache;
import com.github.osvaldopina.linkbuilder.annotation.reader.properties.LinkAnnotationProperties;
import com.github.osvaldopina.linkbuilder.fromcall.MethodCall;
import com.github.osvaldopina.linkbuilder.hal.annotation.HalLinks;
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

public class SpringHateoasHalLinkAnnotationCreator implements LinkAnnotationCreator {


    private BaseUriDiscover baseUriDiscover;

    private AnnotationUriGenerator annotationUriGenerator;

    private IntrospectionUtils introspectionUtils;

    private MethodCallUriGenerator methodCallUriGenerator;

    private AnnotationReader annotationReader;

    private UrlPathContatenator urlPathContatenator = UrlPathContatenator.INSTANCE;

    private EmbeddePropertyCache embeddePropertyCache = new EmbeddePropertyCache(EmbeddedPropertyDiscover.INSTANCE);

    private ObjectMapper objectMapper;


    public SpringHateoasHalLinkAnnotationCreator(BaseUriDiscover baseUriDiscover,
                                                 AnnotationUriGenerator annotationUriGenerator,
                                                 IntrospectionUtils introspectionUtils,
                                                 MethodCallUriGenerator methodCallUriGenerator,
                                                 HalLinkAnnotationReader halLinkAnnotationReader,
                                                 ObjectMapper objectMapper) {

        this.baseUriDiscover = baseUriDiscover;
        this.annotationUriGenerator = annotationUriGenerator;
        this.introspectionUtils = introspectionUtils;
        this.methodCallUriGenerator = methodCallUriGenerator;
        this.annotationReader = new AnnotationReaderCache(halLinkAnnotationReader);
        this.objectMapper = objectMapper;

    }

    @Override
    public boolean canCreate(Method method) {
        return introspectionUtils.
                hasComposedAnnotation(method, HalLinks.class);
    }

    @Override
    public void createAndSetForMethodAnnotations(MethodCall methodCall, Object payload) {
        List<LinkAnnotationProperties> linksProperties = annotationReader.read(methodCall.getMethod());

        for (LinkAnnotationProperties linkProperties : linksProperties) {
            createAndSet(linkProperties, methodCall, payload);
        }

        createAndSetSelfLinkIfNeeded(methodCall, payload);

    }

    @Override
    public boolean canCreate(Object payload) {
        return introspectionUtils.
                hasComposedAnnotation(payload.getClass(), HalLinks.class);
    }

    @Override
    public void createAndSetForResourceAnnotations(MethodCall methodCall, Object payload) {
        List<LinkAnnotationProperties> linksProperties = annotationReader.read(payload.getClass());

        for (LinkAnnotationProperties linkProperties : linksProperties) {
            createAndSet(linkProperties, methodCall, payload);
        }

        Method embeddedReadMethod = embeddePropertyCache.getReaderMethodForHalEmbedded(objectMapper, payload.getClass());

        if (embeddedReadMethod != null &&  Map.class.isAssignableFrom(embeddedReadMethod.getReturnType())) {
            try {
                Map<?,?> embeddedMap = (Map<?,?>) embeddedReadMethod.invoke(payload);
                for(Object embeddedValue : embeddedMap.values()) {
                    if (Collection.class.isAssignableFrom(embeddedValue.getClass())) {
                       Collection<?> embeddedCollectionItems = (Collection<?>) embeddedValue;
                        for(Object embeddedCollectionItem : embeddedCollectionItems) {
                            if (canCreate(embeddedCollectionItem)) {
                                linksProperties = annotationReader.read(embeddedCollectionItem.getClass());
                                for (LinkAnnotationProperties linkProperties : linksProperties) {
                                    createAndSet(linkProperties, methodCall, embeddedCollectionItem);
                                }
                            }
                        }
                    }
                    else {
                        if (canCreate(embeddedValue)) {
                            linksProperties = annotationReader.read(embeddedValue.getClass());

                            for (LinkAnnotationProperties linkProperties : linksProperties) {
                                createAndSet(linkProperties, methodCall, embeddedValue);
                            }
                        }
                    }
                }
            } catch (IllegalAccessException e) {
                throw new LinkBuilderException("internal error!");
            } catch (InvocationTargetException e) {
                throw new LinkBuilderException("internal error!");
            }


        }
        else {

        }

    }

    private void createAndSet(LinkAnnotationProperties linkAnnotationProperties, MethodCall currentMethodCall, Object payload) {
        HalLinkAnnotationProperties halLinkAnnotationProperties = (HalLinkAnnotationProperties) linkAnnotationProperties;
        if (payload instanceof ResourceSupport) {
            String baseUri = baseUriDiscover.getBaseUri();
            String linkUri = annotationUriGenerator.generateUri(linkAnnotationProperties, currentMethodCall, payload);
            ((ResourceSupport) payload).add(
                    new HalLink(urlPathContatenator.concat(baseUri, linkUri),
                            linkAnnotationProperties.getRel(),
                            halLinkAnnotationProperties.getHreflang())
            );
        }
    }

    private void createAndSetSelfLinkIfNeeded(MethodCall currentMethodCall, Object payload) {

        if (introspectionUtils.isEnableSelfFromCurrentCallMethod(currentMethodCall.getMethod())) {

           ((ResourceSupport) payload).add(
                    new HalLink(methodCallUriGenerator.generateUri(currentMethodCall, payload),
                            "self",
                            null)
            );
        }
    }

}
