package com.github.osvaldopina.linkbuilder.template.variablevaluediscover.methodcall.parametervalue.impl;

import static org.easymock.EasyMock.expect;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

import java.util.Arrays;

import com.github.osvaldopina.linkbuilder.LinkBuilderException;
import com.github.osvaldopina.linkbuilder.fromcall.MethodCall;
import com.github.osvaldopina.linkbuilder.template.Variables;
import com.github.osvaldopina.linkbuilder.template.variablevaluediscover.methodcall.parametervalue.ParameterVariableValueDiscover;
import org.easymock.EasyMockRule;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class ParameterValueDiscoverRegistryImplTest extends EasyMockSupport {

	@Rule
	public EasyMockRule mocks = new EasyMockRule(this);

	@Mock
	ParameterVariableValueDiscover parameterVariableValueDiscover;

	@Mock
	Variables variables;

	@Mock
	MethodCall methodCall;

	@Mock
	Object resource;

	int parameterIndex = 0;

	ParameterValueDiscoverRegistryImpl parameterValueDiscoverRegistryImpl;

	@Before
	public void setUp() {
		parameterValueDiscoverRegistryImpl = new ParameterValueDiscoverRegistryImpl(
				Arrays.asList(parameterVariableValueDiscover));

	}

	@Test
	public void get() throws Exception {
		expect(parameterVariableValueDiscover.canDiscover(methodCall, parameterIndex)).andReturn(true);

		replayAll();

		assertThat(parameterValueDiscoverRegistryImpl.get(variables, methodCall, resource, parameterIndex),
				is(sameInstance(parameterVariableValueDiscover)));

		verifyAll();

	}

	@Test(expected = LinkBuilderException.class)
	public void get_noParameterValueDiscoverFound() throws Exception {
		expect(parameterVariableValueDiscover.canDiscover(methodCall, parameterIndex)).andReturn(false);
		expect(methodCall.getMethod()).andReturn(null);

		replayAll();

		assertThat(parameterValueDiscoverRegistryImpl.get(variables, methodCall, resource, parameterIndex),
				is(sameInstance(parameterVariableValueDiscover)));

	}

}