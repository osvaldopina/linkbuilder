package com.github.osvaldopina.linkbuilder.resoucemethod.springhateoas;

import static org.easymock.EasyMock.expect;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.*;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;

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
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;


@Ignore
// TODO reimplement
public class ResourceMethodRegistryFactoryTest extends EasyMockSupport {

	@Rule
	public EasyMockRule mocks = new EasyMockRule(this);

	@Mock
	private RequestMappingHandlerMapping handlerMapping;

	@Mock
	private IntrospectionUtils introspectionUtils;

	private RequestMappingInfo requestMappingInfo;

	@Mock
	private HandlerMethod handlerMethod;

	private Method method = Object.class.getMethods()[0];

	private HashMap<RequestMappingInfo, HandlerMethod> handlerMethods = new HashMap<RequestMappingInfo, HandlerMethod>();

	@TestSubject
	private ResourceMethodRegistryFactory resourceMethodRegistryFactoryImp =
			new ResourceMethodRegistryFactory();



	@Before
	public void setUp() {
		requestMappingInfo = new RequestMappingInfo(null, null, null, null, null, null, null);

		handlerMethods.put(requestMappingInfo, handlerMethod);
	}


	@Test
	public void create() {
		expect(handlerMapping.getHandlerMethods()).andReturn(handlerMethods);
		expect(handlerMethod.getMethod()).andReturn(method);
		expect(introspectionUtils.haveToGenerateTemplateFor(method)).andReturn(true);

		replayAll();

		// TODO remover
	//	List<Method> resourceMethods = resourceMethodRegistryFactoryImp.create(introspectionUtils, handlerMapping);
		List<Method> resourceMethods = resourceMethodRegistryFactoryImp.create(introspectionUtils, null);

		verifyAll();

		assertThat(resourceMethods, hasSize(1));
		assertThat(resourceMethods, hasItem(method));
	}

	@Test
	public void create_haveToGenerateTemplateForIsFalse() {
		expect(handlerMapping.getHandlerMethods()).andReturn(handlerMethods);
		expect(handlerMethod.getMethod()).andReturn(method);
		expect(introspectionUtils.haveToGenerateTemplateFor(method)).andReturn(false);

		replayAll();

		// TODO remover
//		List<Method> resourceMethods = resourceMethodRegistryFactoryImp.create(introspectionUtils, handlerMapping);
		List<Method> resourceMethods = resourceMethodRegistryFactoryImp.create(introspectionUtils, null);

		verifyAll();

		assertThat(resourceMethods, hasSize(0));

	}
}