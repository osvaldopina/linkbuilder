package com.github.osvaldopina.linkbuilder.template.conditionalsubustitution.core;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class DontSubstituteAnyTest {

	private DontSubstituteAny dontSubstituteAny;


	@Before
	public void setUp() {
		dontSubstituteAny = new DontSubstituteAny();
	}

	@Test
	public void shouldSubstitute() {
		assertThat(dontSubstituteAny.shouldSubstitute(null, null), is(false));
	}




}