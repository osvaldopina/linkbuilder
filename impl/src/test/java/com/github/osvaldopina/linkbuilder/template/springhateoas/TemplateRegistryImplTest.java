package com.github.osvaldopina.linkbuilder.template.springhateoas;

import static org.easymock.EasyMock.expect;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

import java.lang.reflect.Method;
import java.util.HashMap;

import com.github.osvaldopina.linkbuilder.LinkBuilderException;
import com.github.osvaldopina.linkbuilder.resoucemethod.ResourceMethodRegistry;
import com.github.osvaldopina.linkbuilder.template.Template;
import com.github.osvaldopina.linkbuilder.template.generation.TemplateGenerator;
import org.easymock.EasyMockRule;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;


public class TemplateRegistryImplTest extends EasyMockSupport {

	@Rule
	public EasyMockRule mocks = new EasyMockRule(this);

	@Mock
	Template template;

	@Mock
	TemplateRegistryFactory templateRegistryFactory;

	@Mock
	private ResourceMethodRegistry resourceMethodRegistry;

	@Mock
	private TemplateGenerator templateGenerator;


	Method method = Object.class.getMethods()[0];

	Method methodNotRegistred = Object.class.getMethods()[1];

	HashMap<Method, Template> templates = new HashMap<Method, Template>();;


	@TestSubject
	TemplateRegistryImpl templateRegistryImpl= new TemplateRegistryImpl(null, null);


	@Before
	public void setUp() {

		templates.put(method, template);

	}



	@Test
	public void getTemplate() throws Exception {
		expect(templateRegistryFactory.createTemplateRegistry(templateGenerator, resourceMethodRegistry)).andReturn(templates);

		replayAll();

		Template methodTemplate = templateRegistryImpl.getTemplate(method);

		verifyAll();

		assertThat(methodTemplate, is(sameInstance(template)));

	}

	@Test(expected = LinkBuilderException.class)
	public void getTemplate_templateNotFound() throws Exception {
		expect(templateRegistryFactory.createTemplateRegistry(templateGenerator, resourceMethodRegistry)).andReturn(templates);

		replayAll();

		templateRegistryImpl.getTemplate(methodNotRegistred);

	}

}