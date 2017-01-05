package com.github.osvaldopina.linkbuilder.template.conditionalsubustitution.core;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class SubstituteAllTest {

	private SubstituteAll substituteAll;


	@Before
	public void setUp() {
		substituteAll = new SubstituteAll();
	}

	@Test
	public void shouldSubstitute() {
		assertThat(substituteAll.shouldSubstitute(null, null), is(true));
	}



}