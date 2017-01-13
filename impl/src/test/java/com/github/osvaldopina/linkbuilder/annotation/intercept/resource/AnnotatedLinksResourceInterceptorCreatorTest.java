package com.github.osvaldopina.linkbuilder.annotation.intercept.resource;

import com.github.osvaldopina.linkbuilder.annotation.linkcreator.LinkAnnotationCreatorRegistry;
import com.github.osvaldopina.linkbuilder.annotation.reader.AnnotationReaderRegistry;
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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.core.Is.is;

public class AnnotatedLinksResourceInterceptorCreatorTest extends EasyMockSupport {

	@Rule
	public EasyMockRule mocks = new EasyMockRule(this);

	@Mock
	AnnotationReaderRegistry annotationReaderRegistry;

	@Mock
	LinkAnnotationCreatorRegistry linkAnnotationCreatorRegistry;

	@TestSubject
	private AnnotatedLinksResourceInterceptorCreator annotatedLinksResourceInterceptorCreator = new AnnotatedLinksResourceInterceptorCreator();


	@Test
	public void interceptMethods() throws Exception {

		Object anyObject = new Object();

		Object interceptedObject = annotatedLinksResourceInterceptorCreator.
				addInterceptorToMethods(anyObject, annotationReaderRegistry, linkAnnotationCreatorRegistry);

		assertThat(AopUtils.isAopProxy(interceptedObject), is(true));

		Advisor[] advisor = ((Advised) interceptedObject).getAdvisors();

		assertThat(advisor.length, is(1));

		Advice advice = advisor[0].getAdvice();

  	   assertThat(advice, is(instanceOf(AnnotatedLinksResourceInterceptor.class)));

	}
}