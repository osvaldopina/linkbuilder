package com.github.osvaldopina.linkbuilder.annotation.linkcreator.springhateoas;

import com.github.osvaldopina.linkbuilder.LinkBuilderException;
import com.github.osvaldopina.linkbuilder.annotation.reader.properties.LinkAnnotationProperties;
import com.github.osvaldopina.linkbuilder.fromcall.MethodCall;
import com.github.osvaldopina.linkbuilder.annotation.linkcreator.LinkAnnotationCreator;
import com.github.osvaldopina.linkbuilder.annotation.linkcreator.LinkAnnotationCreatorRegistry;

import java.lang.reflect.Method;
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
    public LinkAnnotationCreator get(Method method) {
        for(LinkAnnotationCreator linkAnnotationCreator : linkAnnotationCreators) {
            if (linkAnnotationCreator.canCreate(method)) {
                return linkAnnotationCreator;
            }
        }
        throw new LinkBuilderException("Could not find LinkAnnotationPropertiesLinkCreator for method " + method);
    }

    @Override
    public LinkAnnotationCreator get(Object resource) {
        for(LinkAnnotationCreator linkAnnotationCreator : linkAnnotationCreators) {
            if (linkAnnotationCreator.canCreate(resource)) {
                return linkAnnotationCreator;
            }
        }
        throw new LinkBuilderException("Could not find LinkAnnotationPropertiesLinkCreator for resource type" + resource.getClass());
    }

 }
