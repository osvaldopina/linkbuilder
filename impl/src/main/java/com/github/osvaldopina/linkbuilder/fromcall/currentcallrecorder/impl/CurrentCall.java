package com.github.osvaldopina.linkbuilder.fromcall.currentcallrecorder.impl;

import com.github.osvaldopina.linkbuilder.methodtemplate.MethodCall;
import org.springframework.context.annotation.Scope;

@Scope("request")
public class CurrentCall {

    private MethodCall methodCall;


    public MethodCall getMethodCall() {
        return methodCall;
    }

    public void setMethodCall(MethodCall methodCall) {
        this.methodCall = methodCall;
    }
}
