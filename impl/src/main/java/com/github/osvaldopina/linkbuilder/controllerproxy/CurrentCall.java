package com.github.osvaldopina.linkbuilder.controllerproxy;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.lang.reflect.Method;

/**
 * Created by deinf.osvaldo on 16/12/2015.
 */
@Controller
@Scope("request")
public class CurrentCall {

    private Object[] parameters;
    private Method method;

    public void setParameters(Object[] parameters) {
        this.parameters = parameters;
    }

    public Object[] getParameters() {
        return parameters;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Method getMethod() {
        return method;
    }

}
