package com.github.osvaldopina.linkbuilder;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Created by deinf.osvaldo on 15/12/2015.
 */
@Component
public class LinksBuilderFactory implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    public LinksBuilder create() {
        return new LinksBuilder(applicationContext);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
