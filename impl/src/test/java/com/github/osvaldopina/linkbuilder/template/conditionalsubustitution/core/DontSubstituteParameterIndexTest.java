package com.github.osvaldopina.linkbuilder.template.conditionalsubustitution.core;

import static org.easymock.EasyMock.expect;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

import com.github.osvaldopina.linkbuilder.template.Variable;
import org.easymock.EasyMockRule;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class DontSubstituteParameterIndexTest extends EasyMockSupport {

	@Rule
	public EasyMockRule mocks = new EasyMockRule(this);

	@Mock
	Variable variable;

	DontSubstituteParameterIndex dontSubstituteParameterIndex;

	int parameterIndex = 1;

	int otherParameterIndex = 2;

	@Before
	public void setUp() {
		dontSubstituteParameterIndex = new DontSubstituteParameterIndex(parameterIndex);
	}


	@Test
	public void shouldSubstitute_sameParameterIndex() throws Exception {
		expect(variable.getParameterIndex()).andReturn(parameterIndex);

		replayAll();

		assertThat(dontSubstituteParameterIndex.shouldSubstitute(variable, null), is(false));

		verifyAll();
	}

	@Test
	public void shouldSubstitute_otherParameterIndex() throws Exception {
		expect(variable.getParameterIndex()).andReturn(otherParameterIndex);

		replayAll();

		assertThat(dontSubstituteParameterIndex.shouldSubstitute(variable, null), is(true));

		verifyAll();
	}

}