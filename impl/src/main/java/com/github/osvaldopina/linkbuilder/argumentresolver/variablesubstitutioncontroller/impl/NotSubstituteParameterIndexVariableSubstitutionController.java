package com.github.osvaldopina.linkbuilder.argumentresolver.variablesubstitutioncontroller.impl;

import com.github.osvaldopina.linkbuilder.argumentresolver.variablesubstitutioncontroller.VariableSubstitutionController;

import java.lang.reflect.Method;

public class NotSubstituteParameterIndexVariableSubstitutionController implements VariableSubstitutionController {

    private int parameterIndex;

    public NotSubstituteParameterIndexVariableSubstitutionController(int parameterIndex) {
        this.parameterIndex = parameterIndex;
    }

    @Override
    public boolean substitute(Method method, int parameterIndex, String variableName, Object parameterValue) {
        return this.parameterIndex != parameterIndex;
    }
}
