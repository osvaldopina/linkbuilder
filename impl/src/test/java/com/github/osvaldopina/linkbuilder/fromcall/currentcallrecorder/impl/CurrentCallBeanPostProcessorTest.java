package com.github.osvaldopina.linkbuilder.fromcall.currentcallrecorder.impl;

import static org.easymock.EasyMock.expect;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

import com.github.osvaldopina.linkbuilder.annotation.EnableSelfFromCurrentCall;
import com.github.osvaldopina.linkbuilder.annotation.reader.AnnotationReaderRegistry;
import com.github.osvaldopina.linkbuilder.utils.IntrospectionUtils;
import org.easymock.EasyMockRule;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.context.ApplicationContext;

public class CurrentCallBeanPostProcessorTest extends EasyMockSupport {

	@Rule
	public EasyMockRule mocks = new EasyMockRule(this);


	@Mock
	private IntrospectionUtils introspectionUtils;

	@Mock
	private CurrentCallRecorderMethodInterceptorCreator currentCallRecorderMethodInterceptorCreator;

	@Mock
	private ApplicationContext applicationContext;

	@TestSubject
	private CurrentCallBeanPostProcessor currentCallBeanPostProcessor =
			new CurrentCallBeanPostProcessor(introspectionUtils);

	Object bean = new Object();

	Object interceptedBean = new Object();

	Method method = Object.class.getMethods()[0];

	@Test
	public void postProcessBeforeInitialization() throws Exception {
		replayAll();

		Object postProcessedBean = currentCallBeanPostProcessor.postProcessBeforeInitialization(bean, "any-name");

		assertThat(postProcessedBean, is(sameInstance(bean)));

		verifyAll();

	}

	@Test
	public void postProcessAfterInitialization_beanIsNotARestController() throws Exception {
		expect(introspectionUtils.isRestController(bean)).andReturn(false);

		replayAll();

		assertThat(currentCallBeanPostProcessor.postProcessAfterInitialization(bean, "any-name"), is(sameInstance(bean)));

		verifyAll();

	}

	@Test
	public void postProcessAfterInitialization_beanIsRestControllerButHasNoSelfFromCurrentCallMethod() throws Exception {
		expect(introspectionUtils.isRestController(bean)).andReturn(true);
		expect(introspectionUtils.getAnnotatedMethods(bean, EnableSelfFromCurrentCall.class)).andReturn(Collections.EMPTY_SET);

		replayAll();

		assertThat(currentCallBeanPostProcessor.postProcessAfterInitialization(bean, "any-name"), is(sameInstance(bean)));

		verifyAll();

	}

	@Test
	public void postProcessAfterInitialization_beanIsRestControllerButHasSelfFromCurrentCallMethod() throws Exception {
		expect(introspectionUtils.isRestController(bean)).andReturn(true);
		expect(introspectionUtils.getAnnotatedMethods(bean, EnableSelfFromCurrentCall.class)).andReturn(
				new HashSet<Method>(Arrays.asList(method)));
		expect(currentCallRecorderMethodInterceptorCreator.addInterceptorToMethods(bean, applicationContext)).
				andReturn(interceptedBean);

		replayAll();

		Object postProcessedBean = currentCallBeanPostProcessor.postProcessAfterInitialization(bean, "any-name");
		assertThat(postProcessedBean, is(not(sameInstance(bean))));
		assertThat(postProcessedBean, is(sameInstance(interceptedBean)));

		verifyAll();

	}


}