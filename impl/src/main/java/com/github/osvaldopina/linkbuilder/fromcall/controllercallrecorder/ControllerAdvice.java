package com.github.osvaldopina.linkbuilder.fromcall.controllercallrecorder;

import com.github.osvaldopina.linkbuilder.methodtemplate.MethodCall;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

public class ControllerAdvice implements MethodInterceptor {


    private CallRecorder callRecorder;


    public ControllerAdvice(CallRecorder callRecorder) {
        this.callRecorder = callRecorder;
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        callRecorder.record(new MethodCall(invocation.getMethod(), invocation.getArguments()));
        return null;
    }
}
