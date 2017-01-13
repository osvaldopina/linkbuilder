package com.github.osvaldopina.linkbuilder.annotation.intercept.resource;

import com.github.osvaldopina.linkbuilder.annotation.linkcreator.LinkAnnotationCreatorRegistry;
import com.github.osvaldopina.linkbuilder.annotation.reader.AnnotationReader;
import com.github.osvaldopina.linkbuilder.annotation.reader.AnnotationReaderRegistry;
import com.github.osvaldopina.linkbuilder.utils.IntrospectionUtils;
import org.easymock.EasyMockRule;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Rule;
import org.junit.Test;

import java.lang.reflect.Method;

import static org.easymock.EasyMock.expect;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertThat;

public class AnnotatedLinksResourceBeansPostProcessorTest extends EasyMockSupport {

    @Rule
    public EasyMockRule mocks = new EasyMockRule(this);

    @Mock
    AnnotationReaderRegistry annotationReaderRegistry;

    @Mock
    AnnotationReader annotationReader;

    @Mock
    LinkAnnotationCreatorRegistry linkAnnotationCreatorRegistry;

    @Mock
    AnnotatedLinksResourceInterceptorCreator interceptorCreator;

    @Mock
    IntrospectionUtils introspectionUtils;

    Object bean = new Object();

    Object interceptedBean = new Object();

    Method objectFirstMethod = Object.class.getMethods()[0];

    Method[] objectAllMethods = Object.class.getMethods();

    @TestSubject
    private AnnotatedLinksResourceBeansPostProcessor annotatedLinksResourceBeansPostProcessor =
            new AnnotatedLinksResourceBeansPostProcessor(null, null, null);

    public AnnotatedLinksResourceBeansPostProcessorTest() throws Exception {
    }

    @Test
    public void postProcessBeforeInitialization() throws Exception {

        replayAll();

        Object postProcessedBean = annotatedLinksResourceBeansPostProcessor.postProcessBeforeInitialization(bean, "any-name");

        assertThat(postProcessedBean, is(sameInstance(bean)));

        verifyAll();
    }

    @Test
    public void postProcessAfterInitialization() throws Exception {

        expect(introspectionUtils.isRestController(bean)).andReturn(true);
        expect(annotationReaderRegistry.get(objectFirstMethod.getReturnType())).andReturn(annotationReader);
        expect(interceptorCreator.
                addInterceptorToMethods(bean, annotationReaderRegistry, linkAnnotationCreatorRegistry)).andReturn(interceptedBean);

        replayAll();

        assertThat(annotatedLinksResourceBeansPostProcessor.postProcessAfterInitialization(bean, "any-name"),
                is(sameInstance(interceptedBean)));

        verifyAll();
    }

    @Test
	public void postProcessAfterInitialization_noMethodReturnTypeHasAnnotationReader() throws Exception {

		expect(introspectionUtils.isRestController(bean)).andReturn(true);
		for(Method method : objectAllMethods) {
			expect(annotationReaderRegistry.get(method.getReturnType())).andReturn(null);
		}

		replayAll();

		Object postProcessedBean = annotatedLinksResourceBeansPostProcessor.postProcessAfterInitialization(bean, "any-name");

		assertThat(postProcessedBean, is(sameInstance(bean)));

		verifyAll();
	}

	@Test
	public void postProcessAfterInitialization_beanIsNotRestController() throws Exception {

		expect(introspectionUtils.isRestController(bean)).andReturn(false);

		replayAll();

		Object postProcessedBean = annotatedLinksResourceBeansPostProcessor.postProcessAfterInitialization(bean, "any-name");

		assertThat(postProcessedBean, is(sameInstance(bean)));

		verifyAll();
	}

}