package com.github.osvaldopina.linkbuilder.template.variablevaluediscover.methodcall.parametervalue;

import com.github.osvaldopina.linkbuilder.fromcall.MethodCall;
import com.github.osvaldopina.linkbuilder.template.VariableValue;
import com.github.osvaldopina.linkbuilder.template.Variables;
import com.github.osvaldopina.linkbuilder.template.conditionalsubustitution.ConditionalVariableSubstitutionStrategies;

import java.util.Collection;
import java.util.List;

public interface ParameterVariableValueDiscover {


    List<VariableValue> getVariableValues(
            Variables variables, MethodCall methodCall, Object payload, int parameterIndex,
            ConditionalVariableSubstitutionStrategies conditionalVariableSubstitutionStrategies);

    boolean canDiscover(MethodCall methodCall, int parameterIndex);

}
