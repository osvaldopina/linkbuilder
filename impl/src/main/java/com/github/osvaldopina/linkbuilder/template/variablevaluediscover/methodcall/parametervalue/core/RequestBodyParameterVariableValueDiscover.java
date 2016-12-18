package com.github.osvaldopina.linkbuilder.template.variablevaluediscover.methodcall.parametervalue.core;

import com.github.osvaldopina.linkbuilder.fromcall.MethodCall;
import com.github.osvaldopina.linkbuilder.template.VariableValue;
import com.github.osvaldopina.linkbuilder.template.Variables;
import com.github.osvaldopina.linkbuilder.template.conditionalsubustitution.ConditionalVariableSubstitutionStrategies;
import com.github.osvaldopina.linkbuilder.template.variablevaluediscover.methodcall.parametervalue.ParameterVariableValueDiscover;
import com.github.osvaldopina.linkbuilder.utils.IntrospectionUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class RequestBodyParameterVariableValueDiscover implements ParameterVariableValueDiscover {

    private IntrospectionUtils instrospectionUtils;

    public RequestBodyParameterVariableValueDiscover(IntrospectionUtils instrospectionUtils) {
        this.instrospectionUtils = instrospectionUtils;
    }


    @Override
    public List<VariableValue> getVariableValues(Variables variables, MethodCall methodCall,
                                                 Object payload, int parameterIndex, ConditionalVariableSubstitutionStrategies conditionalVariableSubstitutionStrategies) {
        return Collections.EMPTY_LIST;
    }

    @Override
    public boolean canDiscover(MethodCall methodCall, int parameterIndex) {
        return instrospectionUtils.isRequestBodyVariableParameter(methodCall.getMethod(), parameterIndex);
    }
}
