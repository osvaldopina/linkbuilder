package com.github.osvaldopina.linkbuilder.fromcall.selfromcurrentcall;

import static org.easymock.EasyMock.expect;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

import com.github.osvaldopina.linkbuilder.annotation.SelfFromCurrentCall;
import com.github.osvaldopina.linkbuilder.urigeneration.link.methodcall.MethodCallUriGenerator;
import com.github.osvaldopina.linkbuilder.utils.IntrospectionUtils;
import org.easymock.EasyMockRule;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Rule;
import org.junit.Test;

public class SelfFromCurrentCallBeanPostProcessorTest extends EasyMockSupport {

	@Rule
	public EasyMockRule mocks = new EasyMockRule(this);

	@Mock
	private MethodCallUriGenerator methodCallUriGenerator;

	@Mock
	private IntrospectionUtils introspectionUtils;

	@Mock
	private SelfFromCurrentCallMethodInterceptorCreator selfFromCurrentCallMethodInterceptorCreator;

	@TestSubject
	private SelfFromCurrentCallBeanPostProcessor selfFromCurrentCallBeanPostProcessor =
			new SelfFromCurrentCallBeanPostProcessor(null, null);

	Object bean = new Object();

	Object interceptedBean = new Object();

	Method method = Object.class.getMethods()[0];

	@Test
	public void postProcessBeforeInitialization() throws Exception {
		replayAll();

		Object postProcessedBean = selfFromCurrentCallBeanPostProcessor.postProcessBeforeInitialization(bean, "any-name");

		assertThat(postProcessedBean, is(sameInstance(bean)));

		verifyAll();

	}

	@Test
	public void postProcessAfterInitialization_beanIsNotARestController() throws Exception {
		expect(introspectionUtils.isRestController(bean)).andReturn(false);

		replayAll();

		assertThat(selfFromCurrentCallBeanPostProcessor.postProcessAfterInitialization(bean, "any-name"), is(sameInstance(bean)));

		verifyAll();

	}

	@Test
	public void postProcessAfterInitialization_beanIsRestControllerButHasNoSelfFromCurrentCallMethod() throws Exception {
		expect(introspectionUtils.isRestController(bean)).andReturn(true);
		expect(introspectionUtils.getAnnotatedMethods(bean, SelfFromCurrentCall.class)).andReturn(Collections.EMPTY_SET);

		replayAll();

		assertThat(selfFromCurrentCallBeanPostProcessor.postProcessAfterInitialization(bean, "any-name"), is(sameInstance(bean)));

		verifyAll();

	}

	@Test
	public void postProcessAfterInitialization_beanIsRestControllerButHasSelfFromCurrentCallMethod() throws Exception {
		expect(introspectionUtils.isRestController(bean)).andReturn(true);
		expect(introspectionUtils.getAnnotatedMethods(bean, SelfFromCurrentCall.class)).andReturn(
				new HashSet<Method>(Arrays.asList(method)));
		expect(selfFromCurrentCallMethodInterceptorCreator.
				addInterceptorToMethods(bean, methodCallUriGenerator, introspectionUtils)).andReturn(interceptedBean);
		replayAll();

		Object postProcessedBean = selfFromCurrentCallBeanPostProcessor.postProcessAfterInitialization(bean, "any-name");
		assertThat(postProcessedBean, is(not(sameInstance(bean))));
		assertThat(postProcessedBean, is(sameInstance(interceptedBean)));

		verifyAll();

	}


}