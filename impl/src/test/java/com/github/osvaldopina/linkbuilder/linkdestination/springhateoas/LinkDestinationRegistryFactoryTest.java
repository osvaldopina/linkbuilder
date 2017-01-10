package com.github.osvaldopina.linkbuilder.linkdestination.springhateoas;

import static org.easymock.EasyMock.expect;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

import com.github.osvaldopina.linkbuilder.resoucemethod.ResourceMethodRegistry;
import com.github.osvaldopina.linkbuilder.utils.IntrospectionUtils;
import org.easymock.EasyMockRule;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;

public class LinkDestinationRegistryFactoryTest extends EasyMockSupport {

	@Rule
	public EasyMockRule mocks = new EasyMockRule(this);

	@Mock
	private IntrospectionUtils introspectionUtils;

	@Mock
	private ResourceMethodRegistry resourceMethodRegistry;

	private Method method = Object.class.getMethods()[0];

	private Collection<Method> methods = Arrays.asList(Object.class.getMethods()[0]);

	@Mock
	private DestinationIdentityFactorty destinationIdentityFactorty;

	@TestSubject
	private LinkDestinationRegistryFactory linkDestinationRegistryFactory = new LinkDestinationRegistryFactory();

	@Test
	public void createDestinationRegistry_methodRelFromInstrospectionUtils() {
		expect(resourceMethodRegistry.getResourceMethods()).andReturn(methods);
		expect(introspectionUtils.getMethodRel(method)).andReturn("rel");
		expect(destinationIdentityFactorty.destination(Object.class,"rel")).andReturn("destination");

		replayAll();

		Map<String, Method> destinations =  linkDestinationRegistryFactory.
				createDestinationRegistry(introspectionUtils, resourceMethodRegistry);

		verifyAll();

		assertThat(destinations.size(), is(1));
		assertThat(destinations, hasEntry("destination", method));
	}

	@Test
	public void createDestinationRegistry_methodDestinationFromInstrospectionUtils() {
		expect(resourceMethodRegistry.getResourceMethods()).andReturn(methods);
		expect(introspectionUtils.getMethodRel(method)).andReturn(null);
		expect(introspectionUtils.getMethodDestination(method)).andReturn("destination");

		replayAll();

		Map<String, Method> destinations =  linkDestinationRegistryFactory.
				createDestinationRegistry(introspectionUtils, resourceMethodRegistry);

		verifyAll();

		assertThat(destinations.size(), is(1));
		assertThat(destinations, hasEntry("destination", method));


	}

	@Test
	public void createDestinationRegistry_couldNotFindDestination() {
		expect(resourceMethodRegistry.getResourceMethods()).andReturn(methods);
		expect(introspectionUtils.getMethodRel(method)).andReturn(null);
		expect(introspectionUtils.getMethodDestination(method)).andReturn(null);

		replayAll();

		Map<String, Method> destinations =  linkDestinationRegistryFactory.
				createDestinationRegistry(introspectionUtils, resourceMethodRegistry);

		verifyAll();

		assertThat(destinations.size(), is(0));
	}


}