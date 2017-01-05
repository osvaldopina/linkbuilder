package com.github.osvaldopina.linkbuilder.impl.springhateoas;


import com.github.osvaldopina.linkbuilder.LinkBuilder;
import com.github.osvaldopina.linkbuilder.LinksBuilder;
import com.github.osvaldopina.linkbuilder.expression.ExpressionExecutor;
import com.github.osvaldopina.linkbuilder.extension.LinkBuilderExtensionFactoryRegistry;
import com.github.osvaldopina.linkbuilder.fromcall.currentcallrecorder.CurrentCallLocator;
import com.github.osvaldopina.linkbuilder.linkcreator.linkbuilder.LinkPropertiesLinkCreators;
import com.github.osvaldopina.linkbuilder.urigeneration.link.methodcall.MethodCallUriGenerator;
import com.github.osvaldopina.linkbuilder.utils.IntrospectionUtils;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Builder for Links. This builder should be used to create a list of <code>Link</code>. The controller <code>link</code>
 * should be used to create a builder for a link.
 */
class LinksBuilderImpl implements LinksBuilder {

    private Object payload;
    private List<SpringHateoasLinkBuilderImpl> linkBuilders = new ArrayList<SpringHateoasLinkBuilderImpl>();
    private CurrentCallLocator currentCallLocator;
    private  LinkPropertiesLinkCreators linkPropertiesLinkCreators;
    private LinkBuilderExtensionFactoryRegistry linkBuilderExtensionFactoryRegistry;

    protected LinksBuilderImpl(CurrentCallLocator currentCallLocator,
                               LinkPropertiesLinkCreators linkPropertiesLinkCreators,
                               LinkBuilderExtensionFactoryRegistry linkBuilderExtensionFactoryRegistry) {
        this(currentCallLocator, linkPropertiesLinkCreators, linkBuilderExtensionFactoryRegistry, null);
    }

    protected LinksBuilderImpl(
            CurrentCallLocator currentCallLocator,
            LinkPropertiesLinkCreators linkPropertiesLinkCreators,
            LinkBuilderExtensionFactoryRegistry linkBuilderExtensionFactoryRegistry,
            Object payload) {

        this.currentCallLocator = currentCallLocator;
        this.linkPropertiesLinkCreators = linkPropertiesLinkCreators;
        this.linkBuilderExtensionFactoryRegistry = linkBuilderExtensionFactoryRegistry;
        this.payload = payload;
    }

    @Override
    public LinkBuilder link() {
        SpringHateoasLinkBuilderImpl linkBuilder = new SpringHateoasLinkBuilderImpl(
                this,
                currentCallLocator,
                linkPropertiesLinkCreators,
                linkBuilderExtensionFactoryRegistry,
                payload
        );
        linkBuilders.add(linkBuilder);
        return linkBuilder;
    }

    @Override
    public void buildAndSetAll() {

        for (LinkBuilder linkBuilder : linkBuilders) {
            linkBuilder.builAndSet();
        }
    }

    public Object getPayload() {
       return payload;
    }

    public List<SpringHateoasLinkBuilderImpl> getLinkBuilders() {
        return Collections.unmodifiableList(linkBuilders);
    }

    public CurrentCallLocator getCurrentCallLocator() {
        return currentCallLocator;
    }

    public LinkPropertiesLinkCreators getLinkPropertiesLinkCreators() {
        return linkPropertiesLinkCreators;
    }

    public LinkBuilderExtensionFactoryRegistry getLinkBuilderExtensionFactoryRegistry() {
        return linkBuilderExtensionFactoryRegistry;
    }




}