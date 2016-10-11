package com.github.osvaldopina.linkbuilder.argumentresolver.variablesubstitutioncontroller.impl;

import com.github.osvaldopina.linkbuilder.argumentresolver.variablesubstitutioncontroller.VariableSubstitutionController;
import org.springframework.core.MethodParameter;

import java.lang.reflect.Method;

public class SubstituteNoneVariableSubstitutionController implements VariableSubstitutionController {

    @Override
    public boolean substitute(Method method, int parameterIndex, String variableName, Object parameterValue) {
       return false;
    }
}
