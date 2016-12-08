package com.github.osvaldopina.linkbuilder.fromcall;

import java.lang.reflect.Method;

public class MethodCallFactory {

    public static final MethodCallFactory INSTANCE = new MethodCallFactory();

    MethodCallFactory() {

    }

    public MethodCall create(Method method, Object[] params) {
        return new MethodCall(method, params);
    }

}
