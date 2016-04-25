package com.github.osvaldopina.linkbuilder.impl;

import com.github.osvaldopina.linkbuilder.LinksBuilder;
import com.github.osvaldopina.linkbuilder.LinksBuilderFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
class LinksBuilderFactoryImpl implements ApplicationContextAware, LinksBuilderFactory {

    private ApplicationContext applicationContext;

    private Object payload;

    @Override
    public LinksBuilderImpl create() {
        return new LinksBuilderImpl(applicationContext);
    }

    @Override
    public LinksBuilder create(Object payload) {
        return new LinksBuilderImpl(applicationContext, payload);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
