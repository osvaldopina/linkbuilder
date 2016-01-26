package com.github.osvaldopina.linkbuilder;



import org.springframework.context.ApplicationContext;
import org.springframework.hateoas.Link;

import java.util.ArrayList;
import java.util.List;

/**
 * Builder for Links. This builder should be used to create a list of <code>Link</code>. The method <code>link</code>
 * should be used to create a builder for a link.
 */
public class LinksBuilder {

    private List<LinkBuilderImpl> linkBuilders = new ArrayList<LinkBuilderImpl>();

    private ApplicationContext applicationContext;

    protected LinksBuilder(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    /**
     * Creates a new link builder. The linkBuilder should be used to customize the link.
     *
     * @see LinkBuilder
     * @return LinkBuilder Builder to customize the link.
     */
    public LinkBuilder link() {
        LinkBuilderImpl linkBuilder = new LinkBuilderImpl(applicationContext, this);
        linkBuilders.add(linkBuilder);
        return linkBuilder;
    }

    /**
     * Creates the list of all links created by <code>link()</code>.
     *
     * @return List of all links.
     */
    public List<Link> buildAll() {
        List<Link> links = new ArrayList<Link>();
        for(LinkBuilderImpl linkBuilder:linkBuilders) {
            links.add(linkBuilder.build());
        }
        return links;
    }
}
