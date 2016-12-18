package com.github.osvaldopina.linkbuilder.linkdestination.springhateoas;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import com.github.osvaldopina.linkbuilder.LinkBuilderException;
import org.junit.Before;
import org.junit.Test;

public class LinkDestinationRegistryImplTest {


	LinkDestinationRegistryImpl linkDestinationRegistryImpl;

	Map<String, Method> destinations = new HashMap<String, Method>();

	Method method = Object.class.getMethods()[0];

	String destination = "destination";

	@Before
	public void setUp() {

		destinations.put(destination, method);

		linkDestinationRegistryImpl = new LinkDestinationRegistryImpl(destinations);

	}


	@Test
	public void getTemplatedMethod_methodExists() {

		assertThat(linkDestinationRegistryImpl.getTemplatedMethod(destination), is(sameInstance(method)));

	}

	@Test(expected = LinkBuilderException.class)
	public void getTemplatedMethod_methodDoesNotExists() {

		assertThat(linkDestinationRegistryImpl.getTemplatedMethod("any-destination"), is(nullValue()));

	}

	@Test
	public void getDestinations() {

		assertThat(linkDestinationRegistryImpl.getDestinations().entrySet(), hasSize(1));
		assertThat(linkDestinationRegistryImpl.getDestinations().get(destination), is(sameInstance(method)));

	}

}