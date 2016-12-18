package com.github.osvaldopina.linkbuilder.linkdestination.springhateoas;

import static org.easymock.EasyMock.expect;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;

import com.github.osvaldopina.linkbuilder.linkdestination.LinkDestinationRegistry;
import com.github.osvaldopina.linkbuilder.linkdestination.LinkDestinationRegistryFactory;
import com.github.osvaldopina.linkbuilder.resoucemethod.ResourceMethodRegistry;
import com.github.osvaldopina.linkbuilder.utils.IntrospectionUtils;
import org.easymock.EasyMockRule;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Rule;
import org.junit.Test;

public class LinkDestinationRegistryFactoryImplTest extends EasyMockSupport {

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
	private LinkDestinationRegistryFactory linkDestinationRegistryFactory = new LinkDestinationRegistryFactoryImpl(introspectionUtils);




	@Test
	public void createDestinationRegistry_methodRelFromInstrospectionUtils() {
		expect(resourceMethodRegistry.getResourceMethods()).andReturn(methods);
		expect(introspectionUtils.getMethodRel(method)).andReturn("rel");
		expect(destinationIdentityFactorty.destination(Object.class,"rel")).andReturn("destination");

		replayAll();

		LinkDestinationRegistry linkDestinationRegistry = linkDestinationRegistryFactory.
				createDestinationRegistry(resourceMethodRegistry);

		verifyAll();

		assertThat(linkDestinationRegistry.getTemplatedMethod("destination"), is(method));
		assertThat(linkDestinationRegistry.getClass(), is(typeCompatibleWith(LinkDestinationRegistryImpl.class)));
		LinkDestinationRegistryImpl linkDestinationRegistryImpl = (LinkDestinationRegistryImpl) linkDestinationRegistry;
		assertThat(linkDestinationRegistryImpl.getDestinations().entrySet(), hasSize(1));
	}

	@Test
	public void createDestinationRegistry_methodDestinationFromInstrospectionUtils() {
		expect(resourceMethodRegistry.getResourceMethods()).andReturn(methods);
		expect(introspectionUtils.getMethodRel(method)).andReturn(null);
		expect(introspectionUtils.getMethodDestination(method)).andReturn("destination");

		replayAll();

		LinkDestinationRegistry linkDestinationRegistry = linkDestinationRegistryFactory.
				createDestinationRegistry(resourceMethodRegistry);

		verifyAll();

		assertThat(linkDestinationRegistry.getTemplatedMethod("destination"), is(method));
		assertThat(linkDestinationRegistry.getClass(), is(typeCompatibleWith(LinkDestinationRegistryImpl.class)));
		LinkDestinationRegistryImpl linkDestinationRegistryImpl = (LinkDestinationRegistryImpl) linkDestinationRegistry;
		assertThat(linkDestinationRegistryImpl.getDestinations().entrySet(), hasSize(1));


	}

	@Test
	public void createDestinationRegistry_couldNotFindDestination() {
		expect(resourceMethodRegistry.getResourceMethods()).andReturn(methods);
		expect(introspectionUtils.getMethodRel(method)).andReturn(null);
		expect(introspectionUtils.getMethodDestination(method)).andReturn(null);

		replayAll();

		LinkDestinationRegistry linkDestinationRegistry = linkDestinationRegistryFactory.
				createDestinationRegistry(resourceMethodRegistry);

		verifyAll();

		assertThat(linkDestinationRegistry.getClass(), is(typeCompatibleWith(LinkDestinationRegistryImpl.class)));
		LinkDestinationRegistryImpl linkDestinationRegistryImpl = (LinkDestinationRegistryImpl) linkDestinationRegistry;
		assertThat(linkDestinationRegistryImpl.getDestinations().entrySet(), hasSize(0));
	}


}