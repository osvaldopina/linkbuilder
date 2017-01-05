package com.github.osvaldopina.linkbuilder.linkdestination.springhateoas;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class DestinationIdentityFactortyTest {

	DestinationIdentityFactorty destinationIdentityFactorty;


	@Before
	public void setUp() {
		destinationIdentityFactorty = new DestinationIdentityFactorty();
	}

	@Test
	public void destination() throws Exception {

		 assertThat(destinationIdentityFactorty.destination(Object.class, "rel"), is("java.lang.Object:rel"));

	}
}