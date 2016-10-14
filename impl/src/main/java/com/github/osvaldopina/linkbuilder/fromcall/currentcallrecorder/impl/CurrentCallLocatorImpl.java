package com.github.osvaldopina.linkbuilder.fromcall.currentcallrecorder.impl;

import com.github.osvaldopina.linkbuilder.fromcall.currentcallrecorder.CurrentCallLocator;
import com.github.osvaldopina.linkbuilder.methodtemplate.MethodCall;
import org.springframework.beans.factory.annotation.Autowired;

public class CurrentCallLocatorImpl implements CurrentCallLocator{

    @Autowired
    private CurrentCall currentCall;

    @Override
    public MethodCall getCurrentCall() {
        return currentCall.getMethodCall();
    }
}
