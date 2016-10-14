package com.github.osvaldopina.linkbuilder.fromcall.currentcallrecorder.impl;

import com.github.osvaldopina.linkbuilder.fromcall.currentcallrecorder.impl.CurrentCall;
import com.github.osvaldopina.linkbuilder.methodtemplate.MethodCall;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.context.ApplicationContext;

import java.lang.reflect.Method;

public class CurrentCallRecorderMethodInterceptor implements MethodBeforeAdvice {

    private ApplicationContext applicationContext;

    public CurrentCallRecorderMethodInterceptor(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public void before(Method method, Object[] params, Object target) throws Throwable {
        CurrentCall currentCall = applicationContext.getBean(CurrentCall.class);

        currentCall.setMethodCall(new MethodCall(method, params));
    }
}
