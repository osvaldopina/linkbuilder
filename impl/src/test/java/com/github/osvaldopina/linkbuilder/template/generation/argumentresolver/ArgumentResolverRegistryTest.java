package com.github.osvaldopina.linkbuilder.template.generation.argumentresolver;

import static org.easymock.EasyMock.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

import java.lang.reflect.Method;
import java.util.Arrays;

import com.github.osvaldopina.linkbuilder.LinkBuilderException;
import com.github.osvaldopina.linkbuilder.template.generation.argumentresolver.core.QueryParameterArgumentResolver;
import com.github.osvaldopina.linkbuilder.utils.IntrospectionUtils;
import org.easymock.EasyMockRule;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class ArgumentResolverRegistryTest extends EasyMockSupport {

	@Rule
	public EasyMockRule mocks = new EasyMockRule(this);

	@Mock
	ArgumentResolver argumentResolver;

	Method method = Object.class.getMethods()[0];

	int parameterIndex = 1;

	ArgumentResolverRegistry argumentResolverRegistry;

	@Before
	public void setUp() {
		argumentResolverRegistry = new ArgumentResolverRegistry(Arrays.asList(argumentResolver));

	}


	@Test
	public void getArgumentResolver() throws Exception {
		expect(argumentResolver.resolveFor(method, parameterIndex)).andReturn(true);

		replayAll();

		assertThat(argumentResolverRegistry.getArgumentResolver(method, parameterIndex), is(sameInstance(argumentResolver)));

		verifyAll();
	}

	@Test(expected = LinkBuilderException.class)
	public void getArgumentResolver_noArgumentResolverFor() throws Exception {
		expect(argumentResolver.resolveFor(method, parameterIndex)).andReturn(false);

		replayAll();

		assertThat(argumentResolverRegistry.getArgumentResolver(method, parameterIndex), is(sameInstance(argumentResolver)));

	}

}