package com.github.osvaldopina.linkbuilder.template.generation.argumentresolver.core;

import static org.easymock.EasyMock.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

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

public class PathParameterArgumentResolverTest extends EasyMockSupport {

	@Rule
	public EasyMockRule mocks = new EasyMockRule(this);

	@Mock
	IntrospectionUtils introspectionUtils;

	Method method = Object.class.getMethods()[0];

	int parameterIndex = 1;

	@TestSubject
	PathParameterArgumentResolver pathParameterArgumentResolver = new PathParameterArgumentResolver(introspectionUtils);

	@Test
	public void resolveFor_isPathVariableParameter() throws Exception {
		expect(introspectionUtils.isPathVariableParameter(method, parameterIndex)).andReturn(true);

		replayAll();

		assertThat(pathParameterArgumentResolver.resolveFor(method,parameterIndex), is(true));

		verifyAll();

	}

	@Test
	public void resolveFor_isNotPathVariableParameter() throws Exception {
		expect(introspectionUtils.isPathVariableParameter(method, parameterIndex)).andReturn(false);

		replayAll();

		assertThat(pathParameterArgumentResolver.resolveFor(method,parameterIndex), is(false));

		verifyAll();

	}

	@Test
	public void create() throws Exception {
		expect(introspectionUtils.getPathVariableName(method,parameterIndex)).andReturn("variable-name");

		replayAll();

		Variables variables = pathParameterArgumentResolver.create(method,parameterIndex);

		verifyAll();

		assertThat(variables, is(notNullValue()));
		Variable variable = variables.get("variable-name");
		assertThat(variable.getMethod(), is(sameInstance(method)));
		assertThat(variable.getParameterIndex(), is(parameterIndex));
		assertThat(variable.getVariableType(), is(VariableType.PATH));

	}
}