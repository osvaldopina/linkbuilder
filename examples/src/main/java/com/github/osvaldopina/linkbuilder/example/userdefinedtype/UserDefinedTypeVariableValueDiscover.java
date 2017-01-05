package com.github.osvaldopina.linkbuilder.example.userdefinedtype;

import com.github.osvaldopina.linkbuilder.fromcall.MethodCall;
import com.github.osvaldopina.linkbuilder.template.*;
import com.github.osvaldopina.linkbuilder.template.conditionalsubustitution.ConditionalVariableSubstitutionStrategies;
import com.github.osvaldopina.linkbuilder.template.variablevaluediscover.methodcall.parametervalue.ParameterVariableValueDiscover;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class UserDefinedTypeVariableValueDiscover implements ParameterVariableValueDiscover {

    @Override
    public List<VariableValue> getVariableValues(Variables variables, MethodCall methodCall, Object resource, int parameterIndex, ConditionalVariableSubstitutionStrategies conditionalVariableSubstitutionStrategies) {

        List<VariableValue> variableValues = new ArrayList<VariableValue>();

        UserDefinedType userDefinedType = (UserDefinedType) methodCall.getParam(parameterIndex);

        Variable variable = variables.get("value1");

        if (conditionalVariableSubstitutionStrategies.shouldSubstitute(variable, methodCall.getParam(parameterIndex))) {
            variableValues.add(new VariableValue(variable, userDefinedType.getValue1()));
        }

        variable = variables.get("value2");

        if (conditionalVariableSubstitutionStrategies.shouldSubstitute(variable, methodCall.getParam(parameterIndex))) {
            variableValues.add(new VariableValue(variable, userDefinedType.getValue2()));
        }

        return variableValues;
    }

    @Override
    public boolean canDiscover(MethodCall methodCall, int parameterIndex) {
        return UserDefinedType.class.isAssignableFrom(methodCall.getMethod().getParameterTypes()[parameterIndex]);
    }
}
