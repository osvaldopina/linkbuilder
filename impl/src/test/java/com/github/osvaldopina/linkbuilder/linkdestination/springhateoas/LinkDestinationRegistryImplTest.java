package com.github.osvaldopina.linkbuilder.linkdestination.springhateoas;

import static org.easymock.EasyMock.expect;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import com.github.osvaldopina.linkbuilder.LinkBuilderException;
import com.github.osvaldopina.linkbuilder.resoucemethod.ResourceMethodRegistry;
import com.github.osvaldopina.linkbuilder.utils.IntrospectionUtils;
import org.easymock.EasyMockRule;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;


public class LinkDestinationRegistryImplTest  extends EasyMockSupport {

	@Rule
	public EasyMockRule mocks = new EasyMockRule(this);

	@Mock
	private LinkDestinationRegistryFactory linkDestinationRegistryFactory;

	@Mock
	private IntrospectionUtils introspectionUtils;

	@Mock
	private ResourceMethodRegistry resourceMethodRegistry;

	private Map<String, Method> destinations = new HashMap<String, Method>();

	@TestSubject
	LinkDestinationRegistryImpl linkDestinationRegistryImpl = new LinkDestinationRegistryImpl(null, null);

	Method method = Object.class.getMethods()[0];

	String destination = "destination";


	@Before
	public void setUp() {

		destinations.put(destination, method);

	}


	@Test
	public void getTemplatedMethod_methodExists() {
		expect(linkDestinationRegistryFactory.createDestinationRegistry(introspectionUtils, resourceMethodRegistry)).andReturn(destinations);

		replayAll();

		assertThat(linkDestinationRegistryImpl.getTemplatedMethod(destination), is(sameInstance(method)));

		verifyAll();

	}

	@Test(expected = LinkBuilderException.class)
	public void getTemplatedMethod_methodDoesNotExists() {
		expect(linkDestinationRegistryFactory.createDestinationRegistry(introspectionUtils, resourceMethodRegistry)).andReturn(destinations);

		replayAll();

		assertThat(linkDestinationRegistryImpl.getTemplatedMethod("any-destination"), is(nullValue()));

		verifyAll();

	}

}