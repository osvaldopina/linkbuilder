package com.github.osvaldopina.linkbuilder.template.variablevaluediscover.annotation.impl;

import static org.easymock.EasyMock.expect;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.assertThat;

import java.lang.reflect.Method;
import java.util.Arrays;

import com.github.osvaldopina.linkbuilder.annotation.reader.properties.LinkAnnotationParameter;
import com.github.osvaldopina.linkbuilder.annotation.reader.properties.LinkAnnotationProperties;
import com.github.osvaldopina.linkbuilder.expression.ExpressionExecutor;
import com.github.osvaldopina.linkbuilder.fromcall.MethodCall;
import com.github.osvaldopina.linkbuilder.template.*;
import org.easymock.EasyMockRule;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Rule;
import org.junit.Test;

public class AnnotationVariableValuesDiscoverImplTest extends EasyMockSupport {

	@Rule
	public EasyMockRule mocks = new EasyMockRule(this);

	@Mock
	ExpressionExecutor expressionExecutor;

	@Mock
	MethodCall currentMethodCall;

	@Mock
	LinkAnnotationProperties linkAnnotationProperties;

	@Mock
	LinkAnnotationParameter linkAnnotationParameter;

	Method method = Object.class.getMethods()[0];

	Object[] params = new Object[] {};

	Object payload = new Object();

	String variableName = "variable";

	String whenExpression = "when-expression";

	String variableValueExpression = "variable-value-expression";

	Object evaluatedExpressionValue = new Object();

	Variables variables = VariablesFactory.INSTANCE.create(
			Arrays.asList(
					new Variable(variableName, VariableType.QUERY, method,0)
			)
	);

	@TestSubject
	AnnotationVariableValuesDiscoverImpl annotationVariableValuesDiscoverImpl =
			new AnnotationVariableValuesDiscoverImpl(expressionExecutor);

	@Test
	public void getVariableValues() throws Exception {
		expect(linkAnnotationProperties.getParameters()).andReturn(Arrays.asList(linkAnnotationParameter));
		expect(linkAnnotationParameter.getWhen()).andReturn(whenExpression);
		expect(currentMethodCall.getParams()).andReturn(params);
		expect(expressionExecutor.isTrue(whenExpression, payload, params)).andReturn(true);
		expect(linkAnnotationParameter.getName()).andReturn(variableName);
		expect(linkAnnotationParameter.getValue()).andReturn(variableValueExpression);
		expect(expressionExecutor.getValue(variableValueExpression, payload, params)).andReturn(evaluatedExpressionValue);

		replayAll();

		VariableValues variableValues = annotationVariableValuesDiscoverImpl.
				getVariableValues(variables, currentMethodCall, payload, linkAnnotationProperties);

		verifyAll();

		assertThat(variableValues.getVariableValueList(), hasSize(1));
		VariableValue variableValue = variableValues.get(variableName);
		assertThat(variableValue.getValue(), is(evaluatedExpressionValue));
		assertThat(variableValue.getVariable(), is(sameInstance(variables.get(variableName))));


	}

	@Test
	public void getVariableValues_whenExpressionIsNull() throws Exception {
		expect(linkAnnotationProperties.getParameters()).andReturn(Arrays.asList(linkAnnotationParameter));
		expect(linkAnnotationParameter.getWhen()).andReturn(whenExpression);
		expect(currentMethodCall.getParams()).andReturn(params);
		expect(expressionExecutor.isTrue(whenExpression, payload, params)).andReturn(false);

		replayAll();

		VariableValues variableValues = annotationVariableValuesDiscoverImpl.
				getVariableValues(variables, currentMethodCall, payload, linkAnnotationProperties);

		verifyAll();

		assertThat(variableValues.getVariableValueList(), hasSize(0));

	}

}