package com.github.osvaldopina.linkbuilder.template.variablevaluediscover.methodcall.impl;

import static org.easymock.EasyMock.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.*;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import com.github.osvaldopina.linkbuilder.fromcall.MethodCall;
import com.github.osvaldopina.linkbuilder.template.*;
import com.github.osvaldopina.linkbuilder.template.conditionalsubustitution.ConditionalVariableSubstitutionStrategies;
import com.github.osvaldopina.linkbuilder.template.variablevaluediscover.methodcall.parametervalue.ParameterVariableValueDiscover;
import com.github.osvaldopina.linkbuilder.template.variablevaluediscover.methodcall.parametervalue.ParameterVariableValueDiscoverRegistry;
import org.easymock.EasyMockRule;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Rule;
import org.junit.Test;

public class MethodCallVariableValuesDiscoverImplTest extends EasyMockSupport {

	@Rule
	public EasyMockRule mocks = new EasyMockRule(this);

	@Mock
	TemplateRegistry templateRegistry;

	Method method = Object.class.getMethod("equals", Object.class);

	@Mock
	ParameterVariableValueDiscoverRegistry parameterVariableValueDiscoverRegistry;

	@Mock
	MethodCall methodCall;

	String variableName = "variable";

	Object resource = new Object();

	Variable variable = new Variable(variableName, VariableType.QUERY, method,0);

	Variables variables = VariablesFactory.INSTANCE.create(Arrays.asList(variable));

	Object value = new Object();

	List<VariableValue> variableValues = Arrays.asList(new VariableValue(variable, value));

	@Mock
	ConditionalVariableSubstitutionStrategies conditionalVariableSubstitutionStrategies;

	@Mock
	ParameterVariableValueDiscover parameterVariableValueDiscover;

	@TestSubject
	MethodCallVariableValuesDiscoverImpl methodCallVariableValuesDiscoverImpl =
			new MethodCallVariableValuesDiscoverImpl(templateRegistry, parameterVariableValueDiscoverRegistry);


	public MethodCallVariableValuesDiscoverImplTest() throws Exception {
	}


	@Test
	public void getVariableValues() {
		expect(methodCall.getMethod()).andReturn(method);
		expect(parameterVariableValueDiscoverRegistry.get(variables, methodCall, resource,0)).andReturn(parameterVariableValueDiscover);
		expect(parameterVariableValueDiscover.getVariableValues(variables, methodCall, resource, 0, conditionalVariableSubstitutionStrategies)).
				andReturn(variableValues);

		replayAll();

		VariableValues variableValues = methodCallVariableValuesDiscoverImpl.
				getVariableValues(variables, methodCall, resource, conditionalVariableSubstitutionStrategies);


		verifyAll();

		assertThat(variableValues.getVariableValueList(), hasSize(1));
		VariableValue variableValue = variableValues.get(variableName);
		assertThat(variableValue.getValue(), is(value));
		assertThat(variableValue.getVariable(), is(sameInstance(variables.get(variableName))));

	}


}