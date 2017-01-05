package com.github.osvaldopina.linkbuilder.template.generation.springhateoas;

import static org.easymock.EasyMock.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.lang.reflect.Method;

import com.github.osvaldopina.linkbuilder.template.Variables;
import com.github.osvaldopina.linkbuilder.template.VariablesFactory;
import com.github.osvaldopina.linkbuilder.template.generation.argumentresolver.ArgumentResolver;
import com.github.osvaldopina.linkbuilder.template.generation.argumentresolver.ArgumentResolverRegistry;
import org.easymock.EasyMockRule;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Rule;
import org.junit.Test;

public class MethodVariableCreatorTest extends EasyMockSupport {

	@Rule
	public EasyMockRule mocks = new EasyMockRule(this);

	@Mock
	VariablesFactory variablesFactory;

	@Mock
	ArgumentResolverRegistry argumentResolverRegistry;

	@Mock
	ArgumentResolver argumentResolver;

	@Mock
	Variables initialVariables;

	@Mock
	Variables parameterVariables;

	@Mock
	Variables mergedVariables;
	@TestSubject
	MethodVariableCreator methodVariableCreator = new MethodVariableCreator();
	private Method method = MethodVariableCreatorTest.class.getMethod("method", Object.class);

	public MethodVariableCreatorTest() throws Exception {
	}

	@Test
	public void getVariables() throws Exception {
		expect(variablesFactory.create()).andReturn(initialVariables);
		expect(argumentResolverRegistry.getArgumentResolver(method, 0)).andReturn(argumentResolver);
		expect(argumentResolver.create(method, 0)).andReturn(parameterVariables);
		expect(variablesFactory.merge(initialVariables, parameterVariables)).andReturn(mergedVariables);

		replayAll();

		Variables variables = methodVariableCreator.create(argumentResolverRegistry, method);

		verifyAll();

		assertThat(variables, is(sameInstance(mergedVariables)));
	}

	public void method(Object parameter) {

	}
}