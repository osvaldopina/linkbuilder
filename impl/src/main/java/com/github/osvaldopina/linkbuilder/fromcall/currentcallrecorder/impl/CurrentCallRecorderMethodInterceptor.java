package com.github.osvaldopina.linkbuilder.fromcall.currentcallrecorder.impl;

import com.github.osvaldopina.linkbuilder.fromcall.MethodCall;
import com.github.osvaldopina.linkbuilder.fromcall.MethodCallFactory;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.context.ApplicationContext;

import java.lang.reflect.Method;

public class CurrentCallRecorderMethodInterceptor implements MethodBeforeAdvice {

    private ApplicationContext applicationContext;

    private MethodCallFactory methodCallFactory = MethodCallFactory.INSTANCE;

    public CurrentCallRecorderMethodInterceptor(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public void before(Method method, Object[] params, Object target) throws Throwable {
        CurrentCall currentCall = applicationContext.getBean(CurrentCall.class);

        currentCall.setMethodCall(methodCallFactory.create(method, params));
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }
}
