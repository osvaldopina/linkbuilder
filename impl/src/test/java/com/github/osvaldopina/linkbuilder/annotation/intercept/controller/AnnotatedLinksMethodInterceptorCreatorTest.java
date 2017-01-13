package com.github.osvaldopina.linkbuilder.annotation.intercept.controller;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import com.github.osvaldopina.linkbuilder.annotation.reader.AnnotationReaderRegistry;
import com.github.osvaldopina.linkbuilder.annotation.linkcreator.LinkAnnotationCreatorRegistry;
import org.aopalliance.aop.Advice;
import org.easymock.EasyMockRule;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.aop.Advisor;
import org.springframework.aop.framework.Advised;
import org.springframework.aop.support.AopUtils;
// TODO creator x factory
public class AnnotatedLinksMethodInterceptorCreatorTest extends EasyMockSupport {

	@Rule
	public EasyMockRule mocks = new EasyMockRule(this);

	@Mock
	private LinkAnnotationCreatorRegistry linkAnnotationCreatorRegistry;

	@TestSubject
	private AnnotatedLinksMethodInterceptorCreator annotatedLinksMethodInterceptorCreator = new AnnotatedLinksMethodInterceptorCreator();

	@Before
	public void setUp() {
		annotatedLinksMethodInterceptorCreator = new AnnotatedLinksMethodInterceptorCreator();
	}

	@Test
	public void interceptMethods() throws Exception {

		Object anyObject = new Object();

		Object interceptedObject = annotatedLinksMethodInterceptorCreator.
				addInterceptorToMethods(anyObject, linkAnnotationCreatorRegistry);

		assertThat(AopUtils.isAopProxy(interceptedObject), is(true));

		Advisor[] advisor = ((Advised) interceptedObject).getAdvisors();

		assertThat(advisor.length, is(1));

		Advice advice = advisor[0].getAdvice();

  	   assertThat(advice, is(instanceOf(AnnotatedLinksMethodInterceptor.class)));

	}
}