package com.github.osvaldopina.linkbuilder;

/**
 * Factory for <code>LinksBuilder</code> creation. This is the entry point for linkbuilder framework.
 * Just inject it by using <code>@Autowired</code>
 */
public interface LinksBuilderFactory {

    /**
     * Creates a new LinksBuilder
     *
     * @see LinksBuilder
     * @return New LinksBuilder
     */
    LinksBuilder create();
}
