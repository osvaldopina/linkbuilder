package com.github.osvaldopina.linkbuilder.template.springhateoas;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

import java.lang.reflect.Method;
import java.util.HashMap;

import com.github.osvaldopina.linkbuilder.LinkBuilderException;
import com.github.osvaldopina.linkbuilder.template.Template;
import org.easymock.EasyMockRule;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class TemplateRegistryImplTest extends EasyMockSupport {

	@Rule
	public EasyMockRule mocks = new EasyMockRule(this);

	@Mock
	Template template;

	Method method = Object.class.getMethods()[0];

	Method methodNotRegistred = Object.class.getMethods()[1];


	TemplateRegistryImpl templateRegistryImpl;


	@Before
	public void setUp() {

		HashMap<Method, Template> templates = new HashMap<Method, Template>();

		templates.put(method, template);

		templateRegistryImpl = new TemplateRegistryImpl(templates);

	}



	@Test
	public void getTemplate() throws Exception {

		Template methodTemplate = templateRegistryImpl.getTemplate(method);

		assertThat(methodTemplate, is(sameInstance(template)));

	}

	@Test(expected = LinkBuilderException.class)
	public void getTemplate_templateNotFound() throws Exception {

		templateRegistryImpl.getTemplate(methodNotRegistred);

	}

}