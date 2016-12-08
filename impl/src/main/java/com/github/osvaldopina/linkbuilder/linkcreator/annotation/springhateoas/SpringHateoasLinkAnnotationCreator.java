package com.github.osvaldopina.linkbuilder.linkcreator.annotation.springhateoas;

import com.github.osvaldopina.linkbuilder.LinkBuilderException;
import com.github.osvaldopina.linkbuilder.annotation.reader.properties.LinkAnnotationProperties;
import com.github.osvaldopina.linkbuilder.fromcall.MethodCall;
import com.github.osvaldopina.linkbuilder.linkcreator.annotation.LinkAnnotationCreator;
import com.github.osvaldopina.linkbuilder.urigeneration.base.BaseUriDiscover;
import com.github.osvaldopina.linkbuilder.urigeneration.link.annotation.AnnotationUriGenerator;
import com.github.osvaldopina.linkbuilder.urigeneration.link.methodcall.MethodCallUriGenerator;
import com.github.osvaldopina.linkbuilder.utils.IntrospectionUtils;
import com.github.osvaldopina.linkbuilder.utils.UrlPathContatenator;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;

public class SpringHateoasLinkAnnotationCreator implements LinkAnnotationCreator {


    private BaseUriDiscover baseUriDiscover;

    private AnnotationUriGenerator annotationUriGenerator;

    private IntrospectionUtils introspectionUtils;

    private MethodCallUriGenerator methodCallUriGenerator;

    private UrlPathContatenator urlPathContatenator = UrlPathContatenator.INSTANCE;



    public SpringHateoasLinkAnnotationCreator(BaseUriDiscover baseUriDiscover,
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
        return LinkAnnotationProperties.class == linkAnnotationProperties.getClass() &&
                ResourceSupport.class.isAssignableFrom(payload.getClass());
    }

    @Override
    public boolean canCreate(MethodCall currentMethodCall, Object payload) {
        return payload != null && ResourceSupport.class.isAssignableFrom(payload.getClass());
    }


    @Override
    public void createAndSet(LinkAnnotationProperties linkAnnotationProperties, MethodCall currentMethodCall, Object payload) {
        if (payload instanceof ResourceSupport) {
            String baseUri = baseUriDiscover.getBaseUri();
            String linkUri = annotationUriGenerator.generateUri(linkAnnotationProperties, currentMethodCall, payload);
            ((ResourceSupport) payload).add(new Link(urlPathContatenator.concat(baseUri, linkUri),linkAnnotationProperties.getRel()));
        }
        else {
            throw new LinkBuilderException("Can only set link to instances of ResourceSupport by pyaload is " + payload.getClass());
        }
    }

    @Override
    public void createAndSetSelfLinkIfNeeded(MethodCall currentMethodCall, Object payload) {
        if (introspectionUtils.isEnableSelfFromCurrentCallMethod(currentMethodCall.getMethod())) {
            if (payload instanceof ResourceSupport) {
                ((ResourceSupport) payload).add(
                        new Link(methodCallUriGenerator.generateUri(currentMethodCall, payload),"self"));
            }
            else {
                throw new LinkBuilderException("Can only set link to instances of ResourceSupport by pyaload is " + payload.getClass());
            }

        }

    }

}
