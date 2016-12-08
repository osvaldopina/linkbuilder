package com.github.osvaldopina.linkbuilder.fromcall.currentcallrecorder.impl;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;


import com.github.osvaldopina.linkbuilder.annotation.reader.AnnotationReaderRegistry;
import org.aopalliance.aop.Advice;
import org.easymock.EasyMockRule;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.aop.Advisor;
import org.springframework.aop.framework.Advised;
import org.springframework.aop.support.AopUtils;
import org.springframework.context.ApplicationContext;

public class CurrentCallRecorderMethodInterceptorCreatorTest extends EasyMockSupport {

	@Rule
	public EasyMockRule mocks = new EasyMockRule(this);

	@Mock
	private ApplicationContext applicationContext;

	private CurrentCallRecorderMethodInterceptorCreator currentCallRecorderMethodInterceptorCreator;


	@Before
	public void setUp() {
		currentCallRecorderMethodInterceptorCreator = new CurrentCallRecorderMethodInterceptorCreator();
	}

	@Test
	public void addInterceptorToMethods() throws Exception {

		Object anyObject = new Object();

		Object interceptedObject = currentCallRecorderMethodInterceptorCreator.
				 addInterceptorToMethods(anyObject, applicationContext);

		assertThat(AopUtils.isAopProxy(interceptedObject), is(true));

		Advisor[] advisor = ((Advised) interceptedObject).getAdvisors();

		assertThat(advisor.length, is(1));

		Advice advice = advisor[0].getAdvice();

		assertThat(advice, is(instanceOf(CurrentCallRecorderMethodInterceptor.class)));

		CurrentCallRecorderMethodInterceptor currentCallRecorderMethodInterceptor =
				(CurrentCallRecorderMethodInterceptor) advice;

		assertThat(currentCallRecorderMethodInterceptor.getApplicationContext(), sameInstance(applicationContext));


	}
}