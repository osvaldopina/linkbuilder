package com.github.osvaldopina.linkbuilder.template.variablevaluediscover.methodcall.parametervalue.core;

import static org.easymock.EasyMock.expect;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;

import com.github.osvaldopina.linkbuilder.fromcall.MethodCall;
import com.github.osvaldopina.linkbuilder.template.Variable;
import com.github.osvaldopina.linkbuilder.template.VariableValue;
import com.github.osvaldopina.linkbuilder.template.Variables;
import com.github.osvaldopina.linkbuilder.template.conditionalsubustitution.ConditionalVariableSubstitutionStrategies;
import com.github.osvaldopina.linkbuilder.template.conditionalsubustitution.ConditionalVariableSubstitutionStrategy;
import com.github.osvaldopina.linkbuilder.utils.IntrospectionUtils;
import org.easymock.EasyMockRule;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Rule;
import org.junit.Test;

public class PathParameterVariableValueDiscoverTest extends EasyMockSupport {

	@Rule
	public EasyMockRule mocks = new EasyMockRule(this);

	@Mock
	IntrospectionUtils introspectionUtils;

	@TestSubject
	PathParameterVariableValueDiscover pathParameterVariableValueDiscover =
			new PathParameterVariableValueDiscover(introspectionUtils);

	Method method;

	@Mock
	MethodCall methodCall;

	@Mock
	Variables variables;

	@Mock
	Variable variable;

	String variableName = "variable-name";

	Object param = new Object();

	Object payload = new Object();

	@Mock
	ConditionalVariableSubstitutionStrategies conditionalVariableSubstitutionStrategies;


	int parameterIndex =0;


	@Test
	public void getVariableValues() throws Exception {
		expect(methodCall.getMethod()).andReturn(method);
		expect(introspectionUtils.getPathVariableName(method, parameterIndex)).andReturn(variableName);
		expect(variables.get(variableName)).andReturn(variable);
		expect(methodCall.getParam(parameterIndex)).andReturn(param);
		expect(conditionalVariableSubstitutionStrategies.shouldSubstitute(variable, param)).andReturn(true);

		replayAll();

		List<VariableValue> variableValueList = pathParameterVariableValueDiscover.getVariableValues(variables, methodCall, payload, parameterIndex,
				conditionalVariableSubstitutionStrategies);

		verifyAll();

		assertThat(variableValueList, hasSize(1));
		VariableValue variableValue = variableValueList.get(0);
		assertThat(variableValue.getVariable(), is(sameInstance(variable)));
		assertThat(variableValue.getValue(), is(sameInstance(param)));


	}

	@Test
	public void getVariableValues_shouldNotSubstitute() throws Exception {
		expect(methodCall.getMethod()).andReturn(method);
		expect(introspectionUtils.getPathVariableName(method, parameterIndex)).andReturn(variableName);
		expect(variables.get(variableName)).andReturn(variable);
		expect(methodCall.getParam(parameterIndex)).andReturn(param);
		expect(conditionalVariableSubstitutionStrategies.shouldSubstitute(variable, param)).andReturn(false);

		replayAll();

		assertThat(pathParameterVariableValueDiscover.getVariableValues(variables, methodCall, payload, parameterIndex,
				conditionalVariableSubstitutionStrategies), hasSize(0));

		verifyAll();
	}


	@Test
	public void canDiscover() throws Exception {
		expect(methodCall.getMethod()).andReturn(method);
		expect(introspectionUtils.isPathVariableParameter(method,parameterIndex)).andReturn(true);

		replayAll();

		assertThat(pathParameterVariableValueDiscover.canDiscover(methodCall, parameterIndex), is(true));

		verifyAll();

	}

	@Test
	public void canDiscover_notAPathParameter() throws Exception {
		expect(methodCall.getMethod()).andReturn(method);
		expect(introspectionUtils.isPathVariableParameter(method,parameterIndex)).andReturn(false);

		replayAll();

		assertThat(pathParameterVariableValueDiscover.canDiscover(methodCall, parameterIndex), is(false));

		verifyAll();

	}

}