package com.github.osvaldopina.linkbuilder.resoucemethod.springhateoas;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.*;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.Test;

public class ResourceMethodRegistryImplTest {

	Method method = Object.class.getMethods()[0];

	ResourceMethodRegistryImpl resourceMethodRegistryImpl = new ResourceMethodRegistryImpl(Arrays.asList(method));

	@Test
	public void getResourceMethods() throws Exception {

		Collection<Method> resourceMethods = resourceMethodRegistryImpl.getResourceMethods();

		assertThat(resourceMethods, hasSize(1));
		assertThat(resourceMethods.iterator().next(), is(sameInstance(method)));




	}
}