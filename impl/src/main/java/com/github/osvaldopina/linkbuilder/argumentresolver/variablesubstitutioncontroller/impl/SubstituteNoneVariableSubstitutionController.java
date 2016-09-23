package com.github.osvaldopina.linkbuilder.argumentresolver.variablesubstitutioncontroller.impl;

import com.github.osvaldopina.linkbuilder.argumentresolver.variablesubstitutioncontroller.VariableSubstitutionController;
import org.springframework.core.MethodParameter;

public class SubstituteNoneVariableSubstitutionController implements VariableSubstitutionController {

    @Override
    public boolean substitute(MethodParameter methodParameter, String variableName, Object parameterValue) {
       return false;
    }
}
