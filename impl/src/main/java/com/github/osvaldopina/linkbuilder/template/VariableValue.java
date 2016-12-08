package com.github.osvaldopina.linkbuilder.template;

public class VariableValue {

    private Variable variable;

    private Object value;


    public VariableValue(Variable variable, Object value) {
        this.variable = variable;
        this.value = value;
     }


    public Variable getVariable() {
        return variable;
    }

    public Object getValue() {
        return value;
    }


}
