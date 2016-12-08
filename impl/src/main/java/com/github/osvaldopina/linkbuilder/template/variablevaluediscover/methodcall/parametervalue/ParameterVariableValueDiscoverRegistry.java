package com.github.osvaldopina.linkbuilder.template.variablevaluediscover.methodcall.parametervalue;

import com.github.osvaldopina.linkbuilder.fromcall.MethodCall;
import com.github.osvaldopina.linkbuilder.template.Variables;

public interface ParameterVariableValueDiscoverRegistry {


    ParameterVariableValueDiscover get(Variables variables, MethodCall methodCall, Object payload, int parameterIndex);

}
