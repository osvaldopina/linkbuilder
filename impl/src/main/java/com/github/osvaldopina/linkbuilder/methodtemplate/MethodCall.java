package com.github.osvaldopina.linkbuilder.methodtemplate;

import java.lang.reflect.Method;

public class MethodCall {

    private Method method;

    private Object[] params;

    public MethodCall(Method method, Object[] params) {
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
