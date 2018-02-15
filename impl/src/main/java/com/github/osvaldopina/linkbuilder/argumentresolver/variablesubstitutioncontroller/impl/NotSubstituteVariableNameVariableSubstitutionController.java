package com.github.osvaldopina.linkbuilder.argumentresolver.variablesubstitutioncontroller.impl;

import com.github.osvaldopina.linkbuilder.argumentresolver.variablesubstitutioncontroller.VariableSubstitutionController;

import java.lang.reflect.Method;

public class NotSubstituteVariableNameVariableSubstitutionController implements VariableSubstitutionController {

    private String variableName;

    public NotSubstituteVariableNameVariableSubstitutionController(String variableName) {
        this.variableName = variableName;
    }

    @Override
    public boolean substitute(Method method, int parameterIndex, String variableName, Object parameterValue) {
        return ! this.variableName.equals(variableName);
    }
}
