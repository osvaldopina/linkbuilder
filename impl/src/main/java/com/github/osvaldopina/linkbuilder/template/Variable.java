package com.github.osvaldopina.linkbuilder.template;

import java.lang.reflect.Method;

public class Variable {

    private String name;

    private VariableType variableType;

    private Method method;

    private int parameterIndex;


    public Variable(String name, VariableType variableType, Method method, int parameterIndex) {
        this.name = name;
        this.variableType = variableType;
        this.method = method;
        this.parameterIndex = parameterIndex;
    }

    public String getName() {
        return name;
    }

    public VariableType getVariableType() {
        return variableType;
    }

    public Method getMethod() {
        return method;
    }

    public int getParameterIndex() {
        return parameterIndex;
    }

    public void setParameterIndex(int parameterIndex) {
        this.parameterIndex = parameterIndex;
    }


    @Override
    public String toString() {
        return new StringBuilder().append("Variable ").append(name).toString();
    }
}
