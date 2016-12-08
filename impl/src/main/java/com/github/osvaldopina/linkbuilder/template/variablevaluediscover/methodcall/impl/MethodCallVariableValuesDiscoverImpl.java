package com.github.osvaldopina.linkbuilder.template.variablevaluediscover.methodcall.impl;

import com.github.osvaldopina.linkbuilder.fromcall.MethodCall;
import com.github.osvaldopina.linkbuilder.template.TemplateRegistry;
import com.github.osvaldopina.linkbuilder.template.VariableValue;
import com.github.osvaldopina.linkbuilder.template.VariableValues;
import com.github.osvaldopina.linkbuilder.template.Variables;
import com.github.osvaldopina.linkbuilder.template.conditionalsubustitution.ConditionalVariableSubstituionStrategies;
import com.github.osvaldopina.linkbuilder.template.variablevaluediscover.methodcall.MethodCallVariableValuesDiscover;
import com.github.osvaldopina.linkbuilder.template.variablevaluediscover.methodcall.parametervalue.ParameterVariableValueDiscover;
import com.github.osvaldopina.linkbuilder.template.variablevaluediscover.methodcall.parametervalue.ParameterVariableValueDiscoverRegistry;

import java.util.ArrayList;
import java.util.List;

public class MethodCallVariableValuesDiscoverImpl implements MethodCallVariableValuesDiscover {

    private TemplateRegistry templateRegistry;

    private ParameterVariableValueDiscoverRegistry parameterVariableValueDiscoverRegistry;

    public MethodCallVariableValuesDiscoverImpl(
            TemplateRegistry templateRegistry,
            ParameterVariableValueDiscoverRegistry parameterVariableValueDiscoverRegistry) {

        this.templateRegistry = templateRegistry;
        this.parameterVariableValueDiscoverRegistry = parameterVariableValueDiscoverRegistry;
    }

    @Override
    public VariableValues getVariableValues(
            Variables variables, MethodCall destinationMethodCall, Object payload,
            ConditionalVariableSubstituionStrategies conditionalVariableSubstituionStrategies) {

        List<VariableValue> variableValueList = new ArrayList<VariableValue>();

        for (int i = 0; i < destinationMethodCall.getMethod().getParameterTypes().length; i++) {

            ParameterVariableValueDiscover parameterVariableValueDiscover =
                    parameterVariableValueDiscoverRegistry.get(variables, destinationMethodCall, payload, i);

            variableValueList.addAll(
                    parameterVariableValueDiscover.getVariableValues(
                            variables, destinationMethodCall, payload, i, conditionalVariableSubstituionStrategies
                    )
            );


        }

        return new VariableValues(variableValueList);
    }

}
