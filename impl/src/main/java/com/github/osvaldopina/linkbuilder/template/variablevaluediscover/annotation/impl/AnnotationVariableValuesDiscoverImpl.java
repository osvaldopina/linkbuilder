package com.github.osvaldopina.linkbuilder.template.variablevaluediscover.annotation.impl;

import java.util.ArrayList;
import java.util.List;

import com.github.osvaldopina.linkbuilder.annotation.reader.properties.LinkAnnotationParameter;
import com.github.osvaldopina.linkbuilder.annotation.reader.properties.LinkAnnotationProperties;
import com.github.osvaldopina.linkbuilder.expression.ExpressionExecutor;
import com.github.osvaldopina.linkbuilder.fromcall.MethodCall;
import com.github.osvaldopina.linkbuilder.template.Variable;
import com.github.osvaldopina.linkbuilder.template.VariableValue;
import com.github.osvaldopina.linkbuilder.template.VariableValues;
import com.github.osvaldopina.linkbuilder.template.Variables;
import com.github.osvaldopina.linkbuilder.template.variablevaluediscover.annotation.AnnotationVariableValuesDiscover;

public class AnnotationVariableValuesDiscoverImpl implements AnnotationVariableValuesDiscover {

	private ExpressionExecutor expressionExecutor;

	public AnnotationVariableValuesDiscoverImpl(ExpressionExecutor expressionExecutor) {
		this.expressionExecutor = expressionExecutor;
	}


	public VariableValues getVariableValues(
			Variables variables, MethodCall currentMethodCall,
			Object resource,
			LinkAnnotationProperties linkAnnotationProperties) {

		Variable variable;
		List<VariableValue> variableValueList = new ArrayList<VariableValue>();
		for (LinkAnnotationParameter linkAnnotationParameter : linkAnnotationProperties.getParameters()) {
			String when = linkAnnotationParameter.getWhen();
			Object[] params = currentMethodCall.getParams();
			if (when == null || "".equals(when) ||
					expressionExecutor.isTrue(when, resource, params)) {

				variable = variables.get(linkAnnotationParameter.getName());

				variableValueList.add(new VariableValue(variable,
						expressionExecutor.getValue(linkAnnotationParameter.getValue(), resource, params)));
			}
		}

		return new VariableValues(variableValueList);
	}
}
