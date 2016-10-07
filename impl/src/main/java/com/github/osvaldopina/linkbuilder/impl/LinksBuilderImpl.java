package com.github.osvaldopina.linkbuilder.impl;



import com.github.osvaldopina.linkbuilder.LinkBuilder;
import com.github.osvaldopina.linkbuilder.LinksBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.hateoas.Link;

import java.util.ArrayList;
import java.util.List;

/**
 * Builder for Links. This builder should be used to create a list of <code>Link</code>. The method <code>link</code>
 * should be used to create a builder for a link.
 */
class LinksBuilderImpl  implements LinksBuilder {

    private Object payload;

    private List<LinkBuilderImpl> linkBuilders = new ArrayList<LinkBuilderImpl>();

    private ApplicationContext applicationContext;

    protected LinksBuilderImpl(ApplicationContext applicationContext) {
        this(applicationContext, null);
    }

    protected LinksBuilderImpl(ApplicationContext applicationContext, Object payload) {
        this.applicationContext = applicationContext;
        this.payload = payload;
    }

    @Override
    public LinkBuilder link() {
        LinkBuilderImpl linkBuilder = new LinkBuilderImpl(applicationContext, this, payload);
        linkBuilders.add(linkBuilder);
        return linkBuilder;
    }

    @Override
    public List<Link> buildAll() {
        List<Link> links = new ArrayList<Link>();
        for(LinkBuilderImpl linkBuilder:linkBuilders) {
            if (linkBuilder.whenExpressionIsTrue()) {
                links.add(linkBuilder.build());
            }
        }
        return links;
    }
}