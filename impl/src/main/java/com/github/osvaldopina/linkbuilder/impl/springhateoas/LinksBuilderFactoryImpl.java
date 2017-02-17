package com.github.osvaldopina.linkbuilder.impl.springhateoas;

import com.github.osvaldopina.linkbuilder.LinksBuilder;
import com.github.osvaldopina.linkbuilder.LinksBuilderFactory;
import com.github.osvaldopina.linkbuilder.extension.LinkBuilderExtensionFactoryRegistry;
import com.github.osvaldopina.linkbuilder.linkcreator.linkbuilder.LinkPropertiesLinkCreators;

public class LinksBuilderFactoryImpl implements LinksBuilderFactory {

    private LinkPropertiesLinkCreators linkPropertiesLinkCreators;
    private LinkBuilderExtensionFactoryRegistry linkBuilderExtensionFactoryRegistry;

    public LinksBuilderFactoryImpl(
            LinkPropertiesLinkCreators linkPropertiesLinkCreators,
                                   LinkBuilderExtensionFactoryRegistry linkBuilderExtensionFactoryRegistry) {
        this.linkPropertiesLinkCreators = linkPropertiesLinkCreators;
        this.linkBuilderExtensionFactoryRegistry = linkBuilderExtensionFactoryRegistry;
    }

    @Override
    public LinksBuilder create() {
        return new LinksBuilderImpl(linkPropertiesLinkCreators, linkBuilderExtensionFactoryRegistry);
    }

    @Override
    public LinksBuilder create(Object resource) {
        return new LinksBuilderImpl(linkPropertiesLinkCreators, linkBuilderExtensionFactoryRegistry, resource);
    }

}