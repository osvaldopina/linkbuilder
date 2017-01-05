package com.github.osvaldopina.linkbuilder.template.conditionalsubustitution;

import static org.easymock.EasyMock.expect;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;

import com.github.osvaldopina.linkbuilder.template.Variable;
import org.easymock.EasyMockRule;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.junit.Rule;
import org.junit.Test;

public class ConditionalVariableSubstitutionStrategiesTest extends EasyMockSupport {

	@Rule
	public EasyMockRule mocks = new EasyMockRule(this);

	@Mock
	ConditionalVariableSubstitutionStrategy conditionalVariableSubstitutionStrategy;

	@Mock
	Variable variable;

	@Mock
	Object value;


	ConditionalVariableSubstitutionStrategies conditionalVariableSubstitutionStrategies;


	@Test
	public void shouldSubstitute_defaultConstructor() throws Exception {
		conditionalVariableSubstitutionStrategies = new ConditionalVariableSubstitutionStrategies();

		replayAll();
		assertThat(conditionalVariableSubstitutionStrategies.shouldSubstitute(variable, value), is(true));
		verifyAll();

	}

	@Test
	public void shouldSubstitute_addConditionalTrue() throws Exception {
		expect(conditionalVariableSubstitutionStrategy.shouldSubstitute(variable, value)).andReturn(true);


		conditionalVariableSubstitutionStrategies =
				new ConditionalVariableSubstitutionStrategies(Arrays.asList(conditionalVariableSubstitutionStrategy));

		replayAll();
		assertThat(conditionalVariableSubstitutionStrategies.shouldSubstitute(variable, value), is(true));
		verifyAll();

	}

	@Test
	public void shouldSubstitute_addConditionalFalse() throws Exception {
		expect(conditionalVariableSubstitutionStrategy.shouldSubstitute(variable, value)).andReturn(false);


		conditionalVariableSubstitutionStrategies =
				new ConditionalVariableSubstitutionStrategies(Arrays.asList(conditionalVariableSubstitutionStrategy));

		replayAll();
		assertThat(conditionalVariableSubstitutionStrategies.shouldSubstitute(variable, value), is(false));
		verifyAll();

	}
}