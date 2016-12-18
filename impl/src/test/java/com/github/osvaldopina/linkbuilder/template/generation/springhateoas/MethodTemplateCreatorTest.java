package com.github.osvaldopina.linkbuilder.template.generation.springhateoas;

import static org.easymock.EasyMock.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.lang.reflect.Method;
import java.util.Arrays;

import com.github.osvaldopina.linkbuilder.template.*;
import org.easymock.EasyMockRule;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.hateoas.core.AnnotationMappingDiscoverer;

public class MethodTemplateCreatorTest extends EasyMockSupport {

	@Rule
	public EasyMockRule mocks = new EasyMockRule(this);

	Method method = Object.class.getMethods()[0];

	@Mock
	AnnotationMappingDiscoverer annotationMappingDiscoverer;

	Variable var1 = new Variable("var1", VariableType.PATH, method, 0);

	Variable var2 = new Variable("var2", VariableType.QUERY, method, 1);

	Variables variables = VariablesFactory.INSTANCE.create(
			Arrays.asList(var1, var2));

	@TestSubject
	MethodTemplateCreator methodTemplateCreator = new MethodTemplateCreator();


	@Test
	public void create() throws Exception {
		expect(annotationMappingDiscoverer.getMapping(method)).andReturn("{var1}");

		replayAll();

		Template template = methodTemplateCreator.create(method, variables);

		verifyAll();

		assertThat(template, is(notNullValue()));
		assertThat(template.getVariables().getVariableList(), hasSize(2));
		assertThat(template.getVariables().get("var1"), is(sameInstance(var1)));
		assertThat(template.getVariables().get("var2"), is(sameInstance(var2)));
		assertThat(template.getUriTemplate().getTemplate(), is("{var1}{?var2}"));
	}
}