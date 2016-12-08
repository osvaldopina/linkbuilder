package com.github.osvaldopina.linkbuilder.annotation.intercept;

import static org.easymock.EasyMock.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.lang.reflect.Method;
import java.util.Arrays;

import com.github.osvaldopina.linkbuilder.annotation.reader.AnnotationReader;
import com.github.osvaldopina.linkbuilder.annotation.reader.AnnotationReaderRegistry;
import com.github.osvaldopina.linkbuilder.linkcreator.annotation.LinkAnnotationCreatorRegistry;
import com.github.osvaldopina.linkbuilder.utils.IntrospectionUtils;
import org.easymock.EasyMockRule;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Rule;
import org.junit.Test;

public class AnnotatedLinksBeansPostProcessorTest extends EasyMockSupport {

	@Rule
	public EasyMockRule mocks = new EasyMockRule(this);

	@Mock
	AnnotationReaderRegistry annotationReaderRegistry;

	@Mock
	AnnotationReader annotationReader;

	@Mock
	LinkAnnotationCreatorRegistry linkAnnotationCreatorRegistry;

	@Mock
	AnnotatedLinksMethodInterceptorCreator interceptorCreator;

	@Mock
	IntrospectionUtils introspectionUtils;

	Object bean = new Object();

	Object interceptedBean = new Object();

	Method objectFirstMethod = Object.class.getMethods()[0];

	Method[] objectAllMethods = Object.class.getMethods();

	@TestSubject
	private AnnotatedLinksBeansPostProcessor annotatedLinksBeansPostProcessor =
			new AnnotatedLinksBeansPostProcessor(annotationReaderRegistry, linkAnnotationCreatorRegistry, introspectionUtils);

	public AnnotatedLinksBeansPostProcessorTest() throws Exception {
	}

	@Test
	public void postProcessBeforeInitialization() throws Exception {

		replayAll();

		Object postProcessedBean = annotatedLinksBeansPostProcessor.postProcessBeforeInitialization(bean, "any-name");

		assertThat(postProcessedBean, is(sameInstance(bean)));

		verifyAll();
	}

	@Test
	public void postProcessAfterInitialization() throws Exception {

		expect(introspectionUtils.isRestController(bean)).andReturn(true);
		expect(annotationReaderRegistry.get(objectFirstMethod)).andReturn(annotationReader);
		expect(interceptorCreator.
				addInterceptorToMethods(bean, annotationReaderRegistry, linkAnnotationCreatorRegistry)).
				andReturn(interceptedBean);

		replayAll();

		Object postProcessedBean = annotatedLinksBeansPostProcessor.postProcessAfterInitialization(bean, "any-name");

		assertThat(postProcessedBean, is(sameInstance(interceptedBean)));

		verifyAll();
	}

	@Test
	public void postProcessAfterInitialization_noMethodHaveAnnotationReader() throws Exception {

		expect(introspectionUtils.isRestController(bean)).andReturn(true);
		for(Method method : objectAllMethods) {
			expect(annotationReaderRegistry.get(method)).andReturn(null);
		}

		replayAll();

		Object postProcessedBean = annotatedLinksBeansPostProcessor.postProcessAfterInitialization(bean, "any-name");

		assertThat(postProcessedBean, is(sameInstance(bean)));

		verifyAll();
	}


	@Test
	public void postProcessAfterInitialization_beanIsNotRestController() throws Exception {

		expect(introspectionUtils.isRestController(bean)).andReturn(false);

		replayAll();

		Object postProcessedBean = annotatedLinksBeansPostProcessor.postProcessAfterInitialization(bean, "any-name");

		assertThat(postProcessedBean, is(sameInstance(bean)));

		verifyAll();
	}
}