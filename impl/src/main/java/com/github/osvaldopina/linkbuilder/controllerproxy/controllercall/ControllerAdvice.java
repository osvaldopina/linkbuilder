package com.github.osvaldopina.linkbuilder.controllerproxy.controllercall;

import com.github.osvaldopina.linkbuilder.LinkBuilderImpl;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * Created by deinf.osvaldo on 15/12/2015.
 */
public class ControllerAdvice implements MethodInterceptor {


    private LinkBuilderImpl linkBuilder;


    public ControllerAdvice(LinkBuilderImpl linkBuilder) {
        this.linkBuilder = linkBuilder;
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        linkBuilder.setMethod(invocation.getMethod());
        linkBuilder.setParameters(invocation.getArguments());
        return null;
    }
}
