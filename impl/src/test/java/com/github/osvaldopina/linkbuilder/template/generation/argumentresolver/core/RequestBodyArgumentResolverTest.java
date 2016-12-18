package com.github.osvaldopina.linkbuilder.template.generation.argumentresolver.core;

import static org.easymock.EasyMock.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.lang.reflect.Method;

import com.github.osvaldopina.linkbuilder.template.Variable;
import com.github.osvaldopina.linkbuilder.template.VariableType;
import com.github.osvaldopina.linkbuilder.template.Variables;
import com.github.osvaldopina.linkbuilder.utils.IntrospectionUtils;
import org.easymock.EasyMockRule;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Rule;
import org.junit.Test;

public class RequestBodyArgumentResolverTest extends EasyMockSupport {

	@Rule
	public EasyMockRule mocks = new EasyMockRule(this);

	@Mock
	IntrospectionUtils introspectionUtils;

	Method method = Object.class.getMethods()[0];

	int parameterIndex = 1;

	@TestSubject
	RequestBodyArgumentResolver requestBodyArgumentResolver = new RequestBodyArgumentResolver(introspectionUtils);

	@Test
	public void resolveFor_isQueryVariableParameter() throws Exception {
		expect(introspectionUtils.isRequestBodyVariableParameter(method, parameterIndex)).andReturn(true);

		replayAll();

		assertThat(requestBodyArgumentResolver.resolveFor(method, parameterIndex), is(true));

		verifyAll();
	}

	@Test
	public void resolveFor_isNotQueryVariableParameter() throws Exception {
		expect(introspectionUtils.isRequestBodyVariableParameter(method, parameterIndex)).andReturn(false);

		replayAll();

		assertThat(requestBodyArgumentResolver.resolveFor(method, parameterIndex), is(false));

		verifyAll();
	}

	@Test
	public void create() throws Exception {

		replayAll();

		Variables variables = requestBodyArgumentResolver.create(method, parameterIndex);

		verifyAll();

		assertThat(variables, is(notNullValue()));
		assertThat(variables.getVariableList(), hasSize(0));
	}
}