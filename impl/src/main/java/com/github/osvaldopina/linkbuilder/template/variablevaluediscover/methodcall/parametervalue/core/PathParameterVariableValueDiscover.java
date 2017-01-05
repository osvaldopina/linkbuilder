package com.github.osvaldopina.linkbuilder.template.variablevaluediscover.methodcall.parametervalue.core;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.github.osvaldopina.linkbuilder.fromcall.MethodCall;
import com.github.osvaldopina.linkbuilder.template.Variable;
import com.github.osvaldopina.linkbuilder.template.VariableValue;
import com.github.osvaldopina.linkbuilder.template.Variables;
import com.github.osvaldopina.linkbuilder.template.conditionalsubustitution.ConditionalVariableSubstitutionStrategies;
import com.github.osvaldopina.linkbuilder.template.variablevaluediscover.methodcall.parametervalue.ParameterVariableValueDiscover;
import com.github.osvaldopina.linkbuilder.utils.IntrospectionUtils;

public class PathParameterVariableValueDiscover implements ParameterVariableValueDiscover {

	private IntrospectionUtils introspectionUtils;

	public PathParameterVariableValueDiscover(IntrospectionUtils introspectionUtils) {
		this.introspectionUtils = introspectionUtils;
	}

	@Override
	public List<VariableValue> getVariableValues(
			Variables variables, MethodCall methodCall,
			Object payload, int parameterIndex,
			ConditionalVariableSubstitutionStrategies conditionalVariableSubstitutionStrategies) {

		String variableName = introspectionUtils.getPathVariableName(methodCall.getMethod(), parameterIndex);

		Variable variable = variables.get(variableName);

		Object param = methodCall.getParam(parameterIndex);
		if (conditionalVariableSubstitutionStrategies.shouldSubstitute(variable, param)) {
			return Arrays.asList(new VariableValue(variable, param));
		} else {
			return Collections.EMPTY_LIST;
		}
	}

	@Override
	public boolean canDiscover(MethodCall methodCall, int parameterIndex) {
		return introspectionUtils.isPathVariableParameter(methodCall.getMethod(), parameterIndex);
	}
}
