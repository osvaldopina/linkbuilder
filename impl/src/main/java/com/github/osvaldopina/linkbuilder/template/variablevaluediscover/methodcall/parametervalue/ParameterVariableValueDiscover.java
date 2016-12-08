package com.github.osvaldopina.linkbuilder.template.variablevaluediscover.methodcall.parametervalue;

import com.github.osvaldopina.linkbuilder.fromcall.MethodCall;
import com.github.osvaldopina.linkbuilder.template.VariableValue;
import com.github.osvaldopina.linkbuilder.template.Variables;
import com.github.osvaldopina.linkbuilder.template.conditionalsubustitution.ConditionalVariableSubstituionStrategies;

import java.util.Collection;

public interface ParameterVariableValueDiscover {


    Collection<VariableValue> getVariableValues(
            Variables variables, MethodCall methodCall, Object payload, int parameterIndex,
            ConditionalVariableSubstituionStrategies conditionalVariableSubstituionStrategies);

    boolean canDiscover(MethodCall methodCall, int parameterIndex);

}
