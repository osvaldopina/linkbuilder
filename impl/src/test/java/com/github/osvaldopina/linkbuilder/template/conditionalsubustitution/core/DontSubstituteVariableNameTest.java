package com.github.osvaldopina.linkbuilder.template.conditionalsubustitution.core;

import static org.easymock.EasyMock.expect;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import com.github.osvaldopina.linkbuilder.template.Variable;
import org.easymock.EasyMockRule;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class DontSubstituteVariableNameTest extends EasyMockSupport {

	@Rule
	public EasyMockRule mocks = new EasyMockRule(this);

	@Mock
	Variable variable;

	String variableName = "variableName";

	String otherVariableName = "otherVariableName";

	DontSubstituteVariableName dontSubstituteVariableName;


	@Before
	public void setUp() {
		dontSubstituteVariableName = new DontSubstituteVariableName(variableName);
	}

	@Test
	public void shouldSubstituteSameVariableName() {
		expect(variable.getName()).andReturn(variableName);

		replayAll();

		assertThat(dontSubstituteVariableName.shouldSubstitute(variable, null), is(true));

		verifyAll();

	}

	@Test
	public void shouldSubstituteOtherVariableName() {
		expect(variable.getName()).andReturn(otherVariableName);

		replayAll();

		assertThat(dontSubstituteVariableName.shouldSubstitute(variable, null), is(false));

		verifyAll();

	}

}