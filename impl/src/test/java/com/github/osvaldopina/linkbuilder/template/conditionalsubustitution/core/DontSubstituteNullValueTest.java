package com.github.osvaldopina.linkbuilder.template.conditionalsubustitution.core;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class DontSubstituteNullValueTest {

	private DontSubstituteNullValue dontSubstituteNullValue;


	@Before
	public void setUp() {
		dontSubstituteNullValue = new DontSubstituteNullValue();
	}

	@Test
	public void shouldSubstitute_nullValue() throws Exception {
		assertThat(dontSubstituteNullValue.shouldSubstitute(null, null), is(false));

	}

	@Test
	public void shouldSubstitute_nonNullValue() throws Exception {
		assertThat(dontSubstituteNullValue.shouldSubstitute(null, new Object()), is(true));

	}

}