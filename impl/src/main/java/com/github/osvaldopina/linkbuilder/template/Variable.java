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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Variable variable = (Variable) o;

        if (parameterIndex != variable.parameterIndex) return false;
        if (name != null ? !name.equals(variable.name) : variable.name != null) return false;
        if (variableType != variable.variableType) return false;
        return method != null ? method.equals(variable.method) : variable.method == null;

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (variableType != null ? variableType.hashCode() : 0);
        result = 31 * result + (method != null ? method.hashCode() : 0);
        result = 31 * result + parameterIndex;
        return result;
    }
}
