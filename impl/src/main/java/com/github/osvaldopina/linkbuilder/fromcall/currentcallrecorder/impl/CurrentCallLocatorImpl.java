package com.github.osvaldopina.linkbuilder.fromcall.currentcallrecorder.impl;

import com.github.osvaldopina.linkbuilder.fromcall.currentcallrecorder.CurrentCallLocator;
import com.github.osvaldopina.linkbuilder.fromcall.MethodCall;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class CurrentCallLocatorImpl implements CurrentCallLocator, ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public MethodCall getCurrentCall() {
        return applicationContext.getBean(CurrentCall.class).getMethodCall();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
