package com.github.osvaldopina.linkbuilder.fromcall.selfromcurrentcall;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;


import com.github.osvaldopina.linkbuilder.urigeneration.link.methodcall.MethodCallUriGenerator;
import com.github.osvaldopina.linkbuilder.utils.IntrospectionUtils;
import org.aopalliance.aop.Advice;
import org.easymock.EasyMockRule;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.aop.Advisor;
import org.springframework.aop.framework.Advised;
import org.springframework.aop.support.AopUtils;
import org.springframework.context.ApplicationContext;

public class SelfFromCurrentCallMethodInterceptorCreatorTest extends EasyMockSupport {

	@Rule
	public EasyMockRule mocks = new EasyMockRule(this);

	@Mock
	private MethodCallUriGenerator methodCallUriGenerator;

	@Mock
	private IntrospectionUtils introspectionUtils;

	@Mock
	private SelfFromCurrentCallMethodInterceptorCreator selfFromCurrentCallMethodInterceptorCreator;

	@Before
	public void setUp() {
		selfFromCurrentCallMethodInterceptorCreator = new SelfFromCurrentCallMethodInterceptorCreator();
	}

	@Test
	public void addInterceptorToMethods() throws Exception {

		Object anyObject = new Object();

		Object interceptedObject = selfFromCurrentCallMethodInterceptorCreator.
				 addInterceptorToMethods(anyObject, methodCallUriGenerator, introspectionUtils);

		assertThat(AopUtils.isAopProxy(interceptedObject), is(true));

		Advisor[] advisor = ((Advised) interceptedObject).getAdvisors();

		assertThat(advisor.length, is(1));

		Advice advice = advisor[0].getAdvice();

		assertThat(advice, is(instanceOf(SelfFromCurrentCallMethodInterceptor.class)));

	}
}