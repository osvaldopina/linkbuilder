package com.github.osvaldopina.linkbuilder.fromcall.controllercallrecorder;

import com.github.osvaldopina.linkbuilder.fromcall.MethodCall;
import com.github.osvaldopina.linkbuilder.fromcall.MethodCallFactory;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

public class RecordCallInterceptor implements MethodInterceptor {


    private CallRecorder callRecorder;

    private MethodCallFactory methodCallFactory = MethodCallFactory.INSTANCE;


    public RecordCallInterceptor(CallRecorder callRecorder) {
        this.callRecorder = callRecorder;
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        callRecorder.record(methodCallFactory.create(invocation.getMethod(), invocation.getArguments()));
        return null;
    }
}
