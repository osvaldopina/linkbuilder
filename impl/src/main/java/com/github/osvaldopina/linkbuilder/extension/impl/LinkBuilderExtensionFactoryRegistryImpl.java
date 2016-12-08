package com.github.osvaldopina.linkbuilder.extension.impl;

import com.github.osvaldopina.linkbuilder.LinkBuilder;
import com.github.osvaldopina.linkbuilder.LinkBuilderException;
import com.github.osvaldopina.linkbuilder.extension.LinkBuilderExtensionFactory;
import com.github.osvaldopina.linkbuilder.extension.LinkBuilderExtensionFactoryRegistry;

import java.util.ArrayList;
import java.util.List;

public class LinkBuilderExtensionFactoryRegistryImpl implements LinkBuilderExtensionFactoryRegistry {


    private List<LinkBuilderExtensionFactory> linkBuilderExtensionFactories = new ArrayList<LinkBuilderExtensionFactory>();

    public LinkBuilderExtensionFactoryRegistryImpl(List<LinkBuilderExtensionFactory> linkBuilderExtensionFactories) {
        this.linkBuilderExtensionFactories.addAll(linkBuilderExtensionFactories);
    }

    @Override
    public LinkBuilderExtensionFactory get(Class<? extends LinkBuilder> linkBuilderExtensionType) {
        for(LinkBuilderExtensionFactory linkBuilderExtensionFactory: linkBuilderExtensionFactories) {
            if (linkBuilderExtensionFactory.canCreateExtension(linkBuilderExtensionType)) {
                return linkBuilderExtensionFactory;
            }
        }
        throw new LinkBuilderException("Could not find LinkBuilderExtensionFactory for type " + linkBuilderExtensionType);
    }
}
