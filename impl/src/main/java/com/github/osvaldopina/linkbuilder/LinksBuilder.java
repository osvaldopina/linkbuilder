package com.github.osvaldopina.linkbuilder;

import org.springframework.hateoas.Link;

import java.util.List;

/**
 * Builder for Links. This builder should be used to create a list of <code>Link</code>. The method <code>link</code>
 * should be used to create a builder for a link.
 */
public interface LinksBuilder {

    /**
     * Creates a new link builder. The linkBuilder should be used to customize the link.
     *
     * @see LinkBuilder
     * @return LinkBuilder Builder to customize the link.
     */
    LinkBuilder link();

    /**
     * Creates the list of all links created by <code>link()</code>.
     *
     */
    void buildAndSetAll();

}
