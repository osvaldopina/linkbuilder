package com.github.osvaldopina.linkbuilder.template.variablevaluediscover.methodcall.parametervalue.core;

import static org.easymock.EasyMock.expect;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.lang.reflect.Method;
import java.util.List;

import com.github.osvaldopina.linkbuilder.fromcall.MethodCall;
import com.github.osvaldopina.linkbuilder.template.VariableValue;
import com.github.osvaldopina.linkbuilder.template.Variables;
import com.github.osvaldopina.linkbuilder.template.conditionalsubustitution.ConditionalVariableSubstitutionStrategies;
import com.github.osvaldopina.linkbuilder.utils.IntrospectionUtils;
import org.easymock.EasyMockRule;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Rule;
import org.junit.Test;

public class RequestBodyParameterVariableValueDiscoverTest extends EasyMockSupport {

	@Rule
	public EasyMockRule mocks = new EasyMockRule(this);

	@Mock
	IntrospectionUtils introspectionUtils;

	Method method;

	@Mock
	MethodCall methodCall;

	@Mock
	Variables variables;

	@Mock
	Object resource;

	@Mock
	ConditionalVariableSubstitutionStrategies conditionalVariableSubstitutionStrategies;


	int parameterIndex = 0;

	@TestSubject
	RequestBodyParameterVariableValueDiscover requestBodyParameterVariableValueDiscover =
			new RequestBodyParameterVariableValueDiscover(introspectionUtils);

	@Test
	public void getVariableValues() throws Exception {

		replayAll();

		List<VariableValue> variableValueList = requestBodyParameterVariableValueDiscover.
				getVariableValues(variables, methodCall, resource, parameterIndex, conditionalVariableSubstitutionStrategies);

		verifyAll();

		assertThat(variableValueList, hasSize(0));
	}

	@Test
	public void canDiscover() throws Exception {
		expect(methodCall.getMethod()).andReturn(method);
		expect(introspectionUtils.isRequestBodyVariableParameter(method, parameterIndex)).andReturn(true);

		replayAll();

		assertThat(requestBodyParameterVariableValueDiscover.canDiscover(methodCall, parameterIndex), is(true));

		verifyAll();
	}

	@Test
	public void canDiscover_notARequestBodyParameter() throws Exception {
		expect(methodCall.getMethod()).andReturn(method);
		expect(introspectionUtils.isRequestBodyVariableParameter(method, parameterIndex)).andReturn(false);

		replayAll();

		assertThat(requestBodyParameterVariableValueDiscover.canDiscover(methodCall, parameterIndex), is(false));

		verifyAll();
	}
}