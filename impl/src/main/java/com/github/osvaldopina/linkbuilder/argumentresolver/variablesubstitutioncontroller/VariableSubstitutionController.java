package com.github.osvaldopina.linkbuilder.argumentresolver.variablesubstitutioncontroller;

import org.springframework.core.MethodParameter;

import java.lang.reflect.Method;

public interface VariableSubstitutionController {

    boolean substitute(Method method, int parameterIndex, String variableName, Object parameterValue);

}
