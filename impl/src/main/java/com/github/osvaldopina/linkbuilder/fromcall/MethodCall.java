package com.github.osvaldopina.linkbuilder.fromcall;

import java.lang.reflect.Method;

public class MethodCall {

    private Method method;

    private Object[] params;

    protected MethodCall(Method method, Object[] params) {
        this.method = method;
        this.params = params;
    }

    public Method getMethod() {
        return method;
    }

    public Object[] getParams() {
        return params;
    }

    public Object getParam(int paramIndex) {
        return params[paramIndex];
    }
}
