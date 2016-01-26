package com.github.osvaldopina.linkbuilder;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Factory for LinksBuilder creation. This is the entry point forlinkbuilder framework entry point.
 * Just inject it by using <code>@Autowired</code>
 */
@Component
public class LinksBuilderFactory implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    /**
     * Creates a new LinksBuilder
     *
     * @see LinksBuilder
     * @return New LinksBuilder
     */
    public LinksBuilder create() {
        return new LinksBuilder(applicationContext);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
