package com.github.osvaldopina.linkbuilder.template.variablevaluediscover.methodcall;

import com.github.osvaldopina.linkbuilder.fromcall.MethodCall;
import com.github.osvaldopina.linkbuilder.template.VariableValues;
import com.github.osvaldopina.linkbuilder.template.Variables;
import com.github.osvaldopina.linkbuilder.template.conditionalsubustitution.ConditionalVariableSubstituionStrategies;

public interface MethodCallVariableValuesDiscover {

    VariableValues getVariableValues(
            Variables variables, MethodCall destinationMethodCall, Object payload,
            ConditionalVariableSubstituionStrategies conditionalVariableSubstituionStrategies);

}
