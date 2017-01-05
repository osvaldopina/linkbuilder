package com.github.osvaldopina.linkbuilder.template.variablevaluediscover.methodcall.parametervalue.springhateoas;

import com.github.osvaldopina.linkbuilder.fromcall.MethodCall;
import com.github.osvaldopina.linkbuilder.template.Variable;
import com.github.osvaldopina.linkbuilder.template.VariableValue;
import com.github.osvaldopina.linkbuilder.template.Variables;
import com.github.osvaldopina.linkbuilder.template.conditionalsubustitution.ConditionalVariableSubstitutionStrategies;
import com.github.osvaldopina.linkbuilder.template.variablevaluediscover.methodcall.parametervalue.ParameterVariableValueDiscover;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PageableVariableValueDiscover implements ParameterVariableValueDiscover {

    @Override
    public List<VariableValue> getVariableValues(
            Variables variables, MethodCall methodCall, Object payload, int parameterIndex,
            ConditionalVariableSubstitutionStrategies conditionalVariableSubstitutionStrategies) {

        Pageable pageable = (Pageable) methodCall.getParam(parameterIndex);

        List<VariableValue> variableValues = new ArrayList<VariableValue>();

        Variable pageVariable = variables.get("page");
        int pageNumber = pageable.getPageNumber();
        if (conditionalVariableSubstitutionStrategies.shouldSubstitute(pageVariable, pageNumber)) {
            variableValues.add(new VariableValue(pageVariable, pageNumber));
        }

        Variable sizeVariable = variables.get("size");
        int pageSize = pageable.getPageSize();
        if (conditionalVariableSubstitutionStrategies.shouldSubstitute(sizeVariable, pageSize)) {
            variableValues.add(new VariableValue(sizeVariable, pageSize));
        }

        Variable sortVariable = variables.get("sort");
        Sort sort = pageable.getSort();
        if (conditionalVariableSubstitutionStrategies.shouldSubstitute(sortVariable, sort)) {
            variableValues.add(new VariableValue(sortVariable, sort));
        }

        return variableValues;
    }

    @Override
    public boolean canDiscover(MethodCall methodCall, int parameterIndex) {
        return Pageable.class.isAssignableFrom(methodCall.getMethod().getParameterTypes()[parameterIndex]);
    }
}
