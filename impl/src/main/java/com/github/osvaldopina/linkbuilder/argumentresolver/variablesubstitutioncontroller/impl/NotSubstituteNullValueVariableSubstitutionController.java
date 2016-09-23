package com.github.osvaldopina.linkbuilder.argumentresolver.variablesubstitutioncontroller.impl;

import com.github.osvaldopina.linkbuilder.argumentresolver.variablesubstitutioncontroller.VariableSubstitutionController;
import org.springframework.core.MethodParameter;

public class NotSubstituteNullValueVariableSubstitutionController implements VariableSubstitutionController {

    public NotSubstituteNullValueVariableSubstitutionController() {
    }

    @Override
    public boolean substitute(MethodParameter methodParameter, String variableName, Object parameterValue) {
        return parameterValue != null;
    }
}
