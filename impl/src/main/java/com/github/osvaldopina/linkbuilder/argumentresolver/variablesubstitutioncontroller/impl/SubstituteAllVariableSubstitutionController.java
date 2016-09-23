package com.github.osvaldopina.linkbuilder.argumentresolver.variablesubstitutioncontroller.impl;

import com.github.osvaldopina.linkbuilder.argumentresolver.variablesubstitutioncontroller.VariableSubstitutionController;
import org.springframework.core.MethodParameter;

import java.lang.reflect.Method;

public class SubstituteAllVariableSubstitutionController implements VariableSubstitutionController {

    @Override
    public boolean substitute(MethodParameter methodParameter, String variableName, Object parameterValue) {
       return true;
    }
}
