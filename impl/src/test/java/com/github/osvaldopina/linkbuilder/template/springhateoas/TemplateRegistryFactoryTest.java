package com.github.osvaldopina.linkbuilder.template.springhateoas;

import static org.easymock.EasyMock.expect;
import static org.hamcrest.Matchers.hasEntry;
import static org.junit.Assert.assertThat;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.github.osvaldopina.linkbuilder.resoucemethod.ResourceMethodRegistry;
import com.github.osvaldopina.linkbuilder.template.Template;
import com.github.osvaldopina.linkbuilder.template.generation.TemplateGenerator;
import org.easymock.EasyMockRule;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;


public class TemplateRegistryFactoryTest extends EasyMockSupport {

	@Rule
	public EasyMockRule mocks = new EasyMockRule(this);

	@Mock
	TemplateGenerator templateGenerator;

	@Mock
	ResourceMethodRegistry resourceMethodRegistry;

	@Mock
	Template template;

	Method method = Object.class.getMethods()[0];

	List<Method> resourceMethods = Arrays.asList(method);


	@TestSubject
	TemplateRegistryFactory templateRegistryFactory = new TemplateRegistryFactory();

	@Test
	public void createTemplateRegistry() throws Exception {
		expect(resourceMethodRegistry.getResourceMethods()).andReturn(resourceMethods);
		expect(templateGenerator.generate(method)).andReturn(template);


		replayAll();

		Map<Method,Template> templates = templateRegistryFactory.createTemplateRegistry(templateGenerator, resourceMethodRegistry);

		verifyAll();


		assertThat(templates, hasEntry(method, template));

	}
}