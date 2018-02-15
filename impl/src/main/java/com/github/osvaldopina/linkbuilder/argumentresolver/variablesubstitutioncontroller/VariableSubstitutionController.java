package com.github.osvaldopina.linkbuilder.argumentresolver.variablesubstitutioncontroller;

import java.lang.reflect.Method;

public interface VariableSubstitutionController {

    boolean substitute(Method method, int parameterIndex, String variableName, Object parameterValue);

}
