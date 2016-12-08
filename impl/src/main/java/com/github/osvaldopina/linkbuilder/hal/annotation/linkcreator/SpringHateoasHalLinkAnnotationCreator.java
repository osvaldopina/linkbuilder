package com.github.osvaldopina.linkbuilder.hal.annotation.linkcreator;

import java.lang.reflect.Method;

import com.github.osvaldopina.linkbuilder.annotation.reader.properties.LinkAnnotationProperties;
import com.github.osvaldopina.linkbuilder.fromcall.MethodCall;
import com.github.osvaldopina.linkbuilder.hal.annotation.reader.properties.HalLinkAnnotationProperties;
import com.github.osvaldopina.linkbuilder.hal.springhateoas.HalLink;
import com.github.osvaldopina.linkbuilder.linkcreator.annotation.LinkAnnotationCreator;
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

    private UrlPathContatenator urlPathContatenator =UrlPathContatenator.INSTANCE;


    public SpringHateoasHalLinkAnnotationCreator(BaseUriDiscover baseUriDiscover,
                                                 AnnotationUriGenerator annotationUriGenerator,
                                                 IntrospectionUtils introspectionUtils,
                                                 MethodCallUriGenerator methodCallUriGenerator) {

        this.baseUriDiscover = baseUriDiscover;
        this.annotationUriGenerator = annotationUriGenerator;
        this.introspectionUtils = introspectionUtils;
        this.methodCallUriGenerator = methodCallUriGenerator;
    }

    @Override
    public boolean canCreate(LinkAnnotationProperties linkAnnotationProperties, Object payload) {
        return HalLinkAnnotationProperties.class.isAssignableFrom(linkAnnotationProperties.getClass()) &&
                ResourceSupport.class.isAssignableFrom(payload.getClass());
    }

    @Override
    public boolean canCreate(MethodCall currentMethodCall, Object payload) {
        return ResourceSupport.class.isAssignableFrom(payload.getClass());
    }

    @Override
    public void createAndSet(LinkAnnotationProperties linkAnnotationProperties, MethodCall currentMethodCall, Object payload) {
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

    @Override
    public void createAndSetSelfLinkIfNeeded(MethodCall currentMethodCall, Object payload) {

        if (introspectionUtils.isEnableSelfFromCurrentCallMethod(currentMethodCall.getMethod())) {

           ((ResourceSupport) payload).add(
                    new HalLink(methodCallUriGenerator.generateUri(currentMethodCall, payload),
                            "self",
                            null)
            );
        }
    }

}
