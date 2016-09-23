package com.github.osvaldopina.linkbuilder.argumentresolver.variablesubstitutioncontroller;

import org.springframework.core.MethodParameter;

import java.lang.reflect.Method;

public interface VariableSubstitutionController {

    boolean substitute(MethodParameter methodParameter, String variableName, Object parameterValue);

}
