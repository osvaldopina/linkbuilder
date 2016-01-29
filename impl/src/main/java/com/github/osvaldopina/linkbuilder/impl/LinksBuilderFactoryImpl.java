package com.github.osvaldopina.linkbuilder.impl;

import com.github.osvaldopina.linkbuilder.LinksBuilderFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
class LinksBuilderFactoryImpl implements ApplicationContextAware, LinksBuilderFactory {

    private ApplicationContext applicationContext;

    @Override
    public LinksBuilderImpl create() {
        return new LinksBuilderImpl(applicationContext);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
