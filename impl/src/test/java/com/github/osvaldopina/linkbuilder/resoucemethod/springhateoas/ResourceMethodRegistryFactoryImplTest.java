package com.github.osvaldopina.linkbuilder.resoucemethod.springhateoas;

import static org.easymock.EasyMock.expect;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.*;

import java.lang.reflect.Method;
import java.util.HashMap;

import com.github.osvaldopina.linkbuilder.resoucemethod.ResourceMethodRegistry;
import com.github.osvaldopina.linkbuilder.urigeneration.link.methodcall.MethodCallUriGenerator;
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
public class ResourceMethodRegistryFactoryImplTest extends EasyMockSupport {

	@Rule
	public EasyMockRule mocks = new EasyMockRule(this);

	// TODO voltar
//	@Mock
//	private RequestMappingHandlerMapping handlerMapping;

	@Mock
	private IntrospectionUtils introspectionUtils;

	private RequestMappingInfo requestMappingInfo;

	@Mock
	private HandlerMethod handlerMethod;

	private Method method = Object.class.getMethods()[0];

	private HashMap<RequestMappingInfo, HandlerMethod> handlerMethods = new HashMap<RequestMappingInfo, HandlerMethod>();

	@TestSubject
	private ResourceMethodRegistryFactoryImpl resourceMethodRegistryFactoryImp =
			new ResourceMethodRegistryFactoryImpl( introspectionUtils);

//	@TestSubject
//	private ResourceMethodRegistryFactoryImpl resourceMethodRegistryFactoryImp =
//			new ResourceMethodRegistryFactoryImpl(handlerMapping, introspectionUtils);


	@Before
	public void setUp() {
		requestMappingInfo = new RequestMappingInfo(null, null, null, null, null, null, null);

		handlerMethods.put(requestMappingInfo, handlerMethod);
	}


	@Test
	public void create() {
//		expect(handlerMapping.getHandlerMethods()).andReturn(handlerMethods);
		expect(handlerMethod.getMethod()).andReturn(method);
		expect(introspectionUtils.haveToGenerateTemplateFor(method)).andReturn(true);

		replayAll();

		ResourceMethodRegistry resourceMethodRegistry = resourceMethodRegistryFactoryImp.create();

		verifyAll();

		assertThat(resourceMethodRegistry.getResourceMethods(), hasSize(1));
		assertThat(resourceMethodRegistry.getResourceMethods().iterator().next(), is(method));
	}

	@Test
	public void create_haveToGenerateTemplateForIsFalse() {
//		expect(handlerMapping.getHandlerMethods()).andReturn(handlerMethods);
		expect(handlerMethod.getMethod()).andReturn(method);
		expect(introspectionUtils.haveToGenerateTemplateFor(method)).andReturn(false);

		replayAll();

		ResourceMethodRegistry resourceMethodRegistry = resourceMethodRegistryFactoryImp.create();

		verifyAll();

		assertThat(resourceMethodRegistry.getResourceMethods(), hasSize(0));

	}
}