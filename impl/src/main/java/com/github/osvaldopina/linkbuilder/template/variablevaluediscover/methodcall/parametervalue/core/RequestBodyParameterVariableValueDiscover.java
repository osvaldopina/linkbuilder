package com.github.osvaldopina.linkbuilder.template.variablevaluediscover.methodcall.parametervalue.core;

import com.github.osvaldopina.linkbuilder.fromcall.MethodCall;
import com.github.osvaldopina.linkbuilder.template.VariableValue;
import com.github.osvaldopina.linkbuilder.template.Variables;
import com.github.osvaldopina.linkbuilder.template.conditionalsubustitution.ConditionalVariableSubstituionStrategies;
import com.github.osvaldopina.linkbuilder.template.variablevaluediscover.methodcall.parametervalue.ParameterVariableValueDiscover;
import com.github.osvaldopina.linkbuilder.utils.IntrospectionUtils;

import java.util.Collection;
import java.util.Collections;

public class RequestBodyParameterVariableValueDiscover implements ParameterVariableValueDiscover {

    private IntrospectionUtils instrospectionUtils;

    public RequestBodyParameterVariableValueDiscover(IntrospectionUtils instrospectionUtils) {
        this.instrospectionUtils = instrospectionUtils;
    }


    @Override
    public Collection<VariableValue> getVariableValues(Variables variables, MethodCall methodCall,
                                                       Object payload, int parameterIndex, ConditionalVariableSubstituionStrategies conditionalVariableSubstituionStrategies) {
        return Collections.EMPTY_LIST;
    }

    @Override
    public boolean canDiscover(MethodCall methodCall, int parameterIndex) {
        return instrospectionUtils.isRequestBodyVariableParameter(methodCall.getMethod(), parameterIndex);
    }
}
