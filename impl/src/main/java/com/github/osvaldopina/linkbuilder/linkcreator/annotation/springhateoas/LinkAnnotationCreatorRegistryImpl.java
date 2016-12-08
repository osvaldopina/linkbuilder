package com.github.osvaldopina.linkbuilder.linkcreator.annotation.springhateoas;

import com.github.osvaldopina.linkbuilder.LinkBuilderException;
import com.github.osvaldopina.linkbuilder.annotation.reader.properties.LinkAnnotationProperties;
import com.github.osvaldopina.linkbuilder.fromcall.MethodCall;
import com.github.osvaldopina.linkbuilder.linkcreator.annotation.LinkAnnotationCreator;
import com.github.osvaldopina.linkbuilder.linkcreator.annotation.LinkAnnotationCreatorRegistry;

import java.util.ArrayList;
import java.util.List;

public class LinkAnnotationCreatorRegistryImpl implements LinkAnnotationCreatorRegistry {

    private List<LinkAnnotationCreator> linkAnnotationCreators =
            new ArrayList<LinkAnnotationCreator>();


    public LinkAnnotationCreatorRegistryImpl(
            List<LinkAnnotationCreator> linkAnnotationCreators) {

        this.linkAnnotationCreators.addAll(linkAnnotationCreators);
    }

    @Override
    public LinkAnnotationCreator get(LinkAnnotationProperties linkAnnotationProperties, Object payload) {
        for(LinkAnnotationCreator linkAnnotationCreator : linkAnnotationCreators) {
            if (linkAnnotationCreator.canCreate(linkAnnotationProperties, payload)) {
                return linkAnnotationCreator;
            }
        }
        throw new LinkBuilderException("Could not find LinkAnnotationPropertiesLinkCreator for linkProperties type " +
                linkAnnotationProperties.getClass());
    }

    @Override
    public LinkAnnotationCreator get(MethodCall currentMethodCall, Object payload) {
        for(LinkAnnotationCreator linkAnnotationCreator : linkAnnotationCreators) {
            if (linkAnnotationCreator.canCreate(currentMethodCall, payload)) {
                return linkAnnotationCreator;
            }
        }
        throw new LinkBuilderException("Could not find LinkAnnotationPropertiesLinkCreator for current method call ");
    }
}
