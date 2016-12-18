package com.github.osvaldopina.linkbuilder.template;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;

import org.easymock.EasyMockRule;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class VariablesFactoryTest {

	Method method = Object.class.getMethods()[0];

	Variable variable = new Variable("variable", VariableType.QUERY, method, 0);

	Variable one = new Variable("one", VariableType.QUERY, method, 0);

	Variable other = new Variable("other", VariableType.QUERY, method, 0);;

	Variables oneVariables;

	Variables otherVariables;

	VariablesFactory variablesFactory;

	@Before
	public void setUp() {
		variablesFactory = new VariablesFactory();

		oneVariables = new Variables(Arrays.asList(one));
		otherVariables = new Variables(Arrays.asList(other));
	}

	@Test
	public void create() {
		Variables variables = variablesFactory.create();

		assertThat(variables.getVariableList(), hasSize(0));
	}

	@Test
	public void createFromList() {
		Variables variables = variablesFactory.create(Arrays.asList(variable));

		assertThat(variables.getVariableList(), hasSize(1));
		assertThat(variables.getVariableList().get(0), is(sameInstance(variable)));
	}

	@Test
	public void merge() {
		Variables variables = variablesFactory.merge(oneVariables, otherVariables);

		assertThat(variables.getVariableList(), hasSize(2));
		assertThat(variables.getVariableList(), hasItems(one, other));
	}


}