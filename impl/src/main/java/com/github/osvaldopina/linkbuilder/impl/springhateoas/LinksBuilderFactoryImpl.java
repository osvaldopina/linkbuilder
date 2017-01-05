package com.github.osvaldopina.linkbuilder.impl.springhateoas;

import com.github.osvaldopina.linkbuilder.LinksBuilder;
import com.github.osvaldopina.linkbuilder.LinksBuilderFactory;
import com.github.osvaldopina.linkbuilder.extension.LinkBuilderExtensionFactory;
import com.github.osvaldopina.linkbuilder.extension.LinkBuilderExtensionFactoryRegistry;
import com.github.osvaldopina.linkbuilder.fromcall.currentcallrecorder.CurrentCallLocator;
import com.github.osvaldopina.linkbuilder.linkcreator.linkbuilder.LinkPropertiesLinkCreators;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class LinksBuilderFactoryImpl implements LinksBuilderFactory {

    private CurrentCallLocator currentCallLocator;
    private LinkPropertiesLinkCreators linkPropertiesLinkCreators;
    private LinkBuilderExtensionFactoryRegistry linkBuilderExtensionFactoryRegistry;

    public LinksBuilderFactoryImpl(CurrentCallLocator currentCallLocator, LinkPropertiesLinkCreators linkPropertiesLinkCreators,
                                   LinkBuilderExtensionFactoryRegistry linkBuilderExtensionFactoryRegistry) {
        this.currentCallLocator = currentCallLocator;
        this.linkPropertiesLinkCreators = linkPropertiesLinkCreators;
        this.linkBuilderExtensionFactoryRegistry = linkBuilderExtensionFactoryRegistry;
    }

    @Override
    public LinksBuilder create() {
        return new LinksBuilderImpl(currentCallLocator, linkPropertiesLinkCreators, linkBuilderExtensionFactoryRegistry);
    }

    @Override
    public LinksBuilder create(Object payload) {
        return new LinksBuilderImpl(currentCallLocator, linkPropertiesLinkCreators, linkBuilderExtensionFactoryRegistry, payload);
    }

}