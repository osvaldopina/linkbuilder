package com.github.osvaldopina.linkbuilder.fromcall.controllercallrecorder;

import com.github.osvaldopina.linkbuilder.impl.LinkBuilderImpl;
import com.github.osvaldopina.linkbuilder.methodtemplate.MethodCall;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

public class ControllerAdvice implements MethodInterceptor {


    private LinkBuilderImpl linkBuilder;


    public ControllerAdvice(LinkBuilderImpl linkBuilder) {
        this.linkBuilder = linkBuilder;
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        linkBuilder.setMethodCall(new MethodCall(invocation.getMethod(), invocation.getArguments()));
        return null;
    }
}
