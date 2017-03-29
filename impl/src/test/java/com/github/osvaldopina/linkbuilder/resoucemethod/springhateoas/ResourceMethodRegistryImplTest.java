package com.github.osvaldopina.linkbuilder.resoucemethod.springhateoas;

import static org.easymock.EasyMock.expect;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.*;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import com.github.osvaldopina.linkbuilder.utils.IntrospectionUtils;
import org.easymock.EasyMockRule;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;


@Ignore
// todo reimplement
public class ResourceMethodRegistryImplTest extends EasyMockSupport {

	@Rule
	public EasyMockRule mocks = new EasyMockRule(this);

	@Mock
	ResourceMethodRegistryFactory resourceMethodRegistryFactory;

	@Mock
	IntrospectionUtils introspectionUtils;

	@Mock
	RequestMappingHandlerMapping handlerMapping;

	Method method = Object.class.getMethods()[0];

	@TestSubject
			// TODO acertar
//	ResourceMethodRegistryImpl resourceMethodRegistryImpl = new ResourceMethodRegistryImpl(null, null);
	ResourceMethodRegistryImpl resourceMethodRegistryImpl;

	@Test
	public void getResourceMethods() throws Exception {
		// TODO acertar
//		expect(resourceMethodRegistryFactory.create(introspectionUtils, handlerMapping)).andReturn(Arrays.asList(method));

		replayAll();

		Collection<Method> resourceMethods = resourceMethodRegistryImpl.getResourceMethods();

		verifyAll();

		assertThat(resourceMethods, hasSize(1));
		assertThat(resourceMethods, contains(method));




	}
}