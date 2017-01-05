package com.github.osvaldopina.linkbuilder.annotation.intercept.controller;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import com.github.osvaldopina.linkbuilder.annotation.reader.AnnotationReaderRegistry;
import com.github.osvaldopina.linkbuilder.annotation.linkcreator.LinkAnnotationCreatorRegistry;
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

public class AnnotatedLinksResourceInterceptorCreatorTest extends EasyMockSupport {

	@Rule
	public EasyMockRule mocks = new EasyMockRule(this);

	@Mock
	private AnnotationReaderRegistry annotationReaderRegistry;

	@Mock
	private LinkAnnotationCreatorRegistry linkAnnotationCreatorRegistry;

	private AnnotatedLinksMethodInterceptorCreator annotatedLinksMethodInterceptorCreator;

	@Before
	public void setUp() {
		annotatedLinksMethodInterceptorCreator = new AnnotatedLinksMethodInterceptorCreator();
	}

	@Test
	public void interceptMethods() throws Exception {

		Object anyObject = new Object();

		Object interceptedObject = annotatedLinksMethodInterceptorCreator.
				addInterceptorToMethods(anyObject, annotationReaderRegistry, linkAnnotationCreatorRegistry);

		assertThat(AopUtils.isAopProxy(interceptedObject), is(true));

		Advisor[] advisor = ((Advised) interceptedObject).getAdvisors();

		assertThat(advisor.length, is(1));

		Advice advice = advisor[0].getAdvice();

  	   assertThat(advice, is(instanceOf(AnnotatedLinksMethodInterceptor.class)));

		AnnotatedLinksMethodInterceptor annotatedLinksMethodInterceptor = (AnnotatedLinksMethodInterceptor) advice;

//		assertThat(annotatedLinksMethodInterceptor.getAnnotationReaderRegistry(), sameInstance(annotationReaderRegistry));
//		assertThat(annotatedLinksMethodInterceptor.getLinkCreatorRegistry(), sameInstance(linkAnnotationCreatorRegistry));

	}
}