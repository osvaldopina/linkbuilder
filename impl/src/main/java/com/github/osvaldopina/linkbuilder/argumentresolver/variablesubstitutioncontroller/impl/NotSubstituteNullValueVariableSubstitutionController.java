package com.github.osvaldopina.linkbuilder.argumentresolver.variablesubstitutioncontroller.impl;

import com.github.osvaldopina.linkbuilder.argumentresolver.variablesubstitutioncontroller.VariableSubstitutionController;

import java.lang.reflect.Method;

public class NotSubstituteNullValueVariableSubstitutionController implements VariableSubstitutionController {

    public NotSubstituteNullValueVariableSubstitutionController() {
    }

    @Override
    public boolean substitute(Method method, int parameterIndexMethodParameter, String variableName, Object parameterValue) {
        return parameterValue != null;
    }
}
