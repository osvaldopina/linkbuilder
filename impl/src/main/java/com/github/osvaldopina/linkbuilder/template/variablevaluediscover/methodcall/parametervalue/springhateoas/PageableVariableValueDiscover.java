package com.github.osvaldopina.linkbuilder.template.variablevaluediscover.methodcall.parametervalue.springhateoas;

import com.github.osvaldopina.linkbuilder.fromcall.MethodCall;
import com.github.osvaldopina.linkbuilder.template.VariableValue;
import com.github.osvaldopina.linkbuilder.template.Variables;
import com.github.osvaldopina.linkbuilder.template.conditionalsubustitution.ConditionalVariableSubstituionStrategies;
import com.github.osvaldopina.linkbuilder.template.variablevaluediscover.methodcall.parametervalue.ParameterVariableValueDiscover;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PageableVariableValueDiscover implements ParameterVariableValueDiscover {

    @Override
    public Collection<VariableValue> getVariableValues(
            Variables variables, MethodCall methodCall, Object payload, int parameterIndex,
            ConditionalVariableSubstituionStrategies conditionalVariableSubstituionStrategies) {

        Pageable pageable = (Pageable) methodCall.getParam(parameterIndex);

        List<VariableValue> variableValues = new ArrayList<VariableValue>();


        if (conditionalVariableSubstituionStrategies.shouldSubstitute(variables.get("page"), pageable.getPageNumber())) {
            variableValues.add(new VariableValue(variables.get("page"), pageable.getPageNumber()));
        }
        if (conditionalVariableSubstituionStrategies.shouldSubstitute(variables.get("size"), pageable.getPageSize())) {
            variableValues.add(new VariableValue(variables.get("size"), pageable.getPageSize()));
        }
        if (conditionalVariableSubstituionStrategies.shouldSubstitute(variables.get("sort"), pageable.getPageSize())) {
            variableValues.add(new VariableValue(variables.get("sort"), pageable.getSort()));
        }

        return variableValues;
    }

    @Override
    public boolean canDiscover(MethodCall methodCall, int parameterIndex) {
       // return Pageable.class.isAssignableFrom(methodCall.getMethod().getParameterTypes()[parameterIndex]);
        return false;
    }
}
