package com.github.osvaldopina.linkbuilder.annotation.intercept;

import static org.easymock.EasyMock.*;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import com.github.osvaldopina.linkbuilder.annotation.reader.AnnotationReader;
import com.github.osvaldopina.linkbuilder.annotation.reader.AnnotationReaderRegistry;
import com.github.osvaldopina.linkbuilder.annotation.reader.properties.LinkAnnotationProperties;
import com.github.osvaldopina.linkbuilder.fromcall.MethodCall;
import com.github.osvaldopina.linkbuilder.fromcall.MethodCallFactory;
import com.github.osvaldopina.linkbuilder.linkcreator.annotation.LinkAnnotationCreator;
import com.github.osvaldopina.linkbuilder.linkcreator.annotation.LinkAnnotationCreatorRegistry;
import org.easymock.*;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.hateoas.ResourceSupport;

public class AnnotatedLinksMethodInterceptorTest  extends EasyMockSupport {

	@Rule
	public EasyMockRule mocks = new EasyMockRule(this);

	@Mock
	AnnotationReaderRegistry annotationReaderRegistry;

	@Mock
	AnnotationReader annotationReader;

	@Mock
	LinkAnnotationProperties linkProperties;

	List<LinkAnnotationProperties> linkPropertiesList;

	@Mock
	LinkAnnotationCreator linkCreator;

	@Mock
	LinkAnnotationCreatorRegistry linkCreatorRegistry;

	@Mock
	MethodCallFactory methodCallFactory;

	@Mock
	MethodCall methodCall;

	Object returnValue = new Object();

	Method method = Object.class.getMethod("toString");

	Object[] args = {};

	Object target = new Object();


	@TestSubject
	AnnotatedLinksMethodInterceptor annotatedLinksMethodInterceptor =
			new AnnotatedLinksMethodInterceptor(annotationReaderRegistry, linkCreatorRegistry);


	public AnnotatedLinksMethodInterceptorTest() throws Exception {
	}

	@Before
	public void setUp() {
		linkPropertiesList = Arrays.asList(linkProperties);
	}

	@Test
	public void afterReturning_nullReturnValue() throws Throwable {

		replayAll();

		annotatedLinksMethodInterceptor.afterReturning(null, method, args, target);

		verifyAll();
	}

	@Test
	public void afterReturning_returnValueNotResourceSupportInstance() throws Throwable {

		expect(annotationReaderRegistry.get(method)).andReturn(null);


		replayAll();

		annotatedLinksMethodInterceptor.afterReturning(returnValue, method, args, target);

		verifyAll();
	}

	@Test
	public void afterReturning_linkCreatorCanCreateSelfLink() throws Throwable {

		expect(annotationReaderRegistry.get(method)).andReturn(annotationReader);
		expect(annotationReader.read(method)).andReturn(linkPropertiesList);
		expect(methodCallFactory.create(method, args)).andReturn(methodCall);
		expect(linkCreatorRegistry.get(linkProperties, returnValue)).andReturn(linkCreator);
		linkCreator.createAndSet(linkProperties, methodCall, returnValue);
		expectLastCall();
		expect(linkCreator.canCreate(methodCall, returnValue)).andReturn(true);
		linkCreator.createAndSetSelfLinkIfNeeded(methodCall, returnValue);

		replayAll();

		annotatedLinksMethodInterceptor.afterReturning(returnValue, method, args, target);

		verifyAll();
	}

	@Test
	public void afterReturning_linkCreatorCannotCreateSelfLink() throws Throwable {

		expect(annotationReaderRegistry.get(method)).andReturn(annotationReader);
		expect(annotationReader.read(method)).andReturn(linkPropertiesList);
		expect(methodCallFactory.create(method, args)).andReturn(methodCall);
		expect(linkCreatorRegistry.get(linkProperties, returnValue)).andReturn(linkCreator);
		linkCreator.createAndSet(linkProperties, methodCall, returnValue);
		expectLastCall();
		expect(linkCreator.canCreate(methodCall, returnValue)).andReturn(false);
		expect(linkCreatorRegistry.get(methodCall, returnValue)).andReturn(linkCreator);
		linkCreator.createAndSetSelfLinkIfNeeded(methodCall, returnValue);

		replayAll();

		annotatedLinksMethodInterceptor.afterReturning(returnValue, method, args, target);

		verifyAll();
	}
}