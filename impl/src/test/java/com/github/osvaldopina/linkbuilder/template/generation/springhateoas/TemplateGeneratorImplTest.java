package com.github.osvaldopina.linkbuilder.template.generation.springhateoas;

import static org.easymock.EasyMock.expect;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

import java.lang.reflect.Method;

import com.github.osvaldopina.linkbuilder.template.Template;
import com.github.osvaldopina.linkbuilder.template.Variables;
import com.github.osvaldopina.linkbuilder.template.generation.argumentresolver.ArgumentResolverRegistry;
import org.easymock.EasyMockRule;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Rule;
import org.junit.Test;

public class TemplateGeneratorImplTest extends EasyMockSupport {

	@Rule
	public EasyMockRule mocks = new EasyMockRule(this);

	@Mock
	ArgumentResolverRegistry argumentResolverRegistry;

	@Mock
	MethodVariableCreator methodVariableCreator;

	@Mock
	MethodTemplateCreator methodTemplateCreator;

	@Mock
	Template template;

	@Mock
	Variables variables;

	Method method = Object.class.getMethods()[0];

	@TestSubject
	TemplateGeneratorImpl templateGeneratorImp = new TemplateGeneratorImpl(argumentResolverRegistry);

	@Test
	public void generate() throws Exception {
		expect(methodVariableCreator.create(argumentResolverRegistry, method)).andReturn(variables);
		expect(methodTemplateCreator.create(method, variables)).andReturn(template);

		replayAll();

		assertThat(templateGeneratorImp.generate(method), is(sameInstance(template)));

		verifyAll();

	}
}