package com.github.osvaldopina.linkbuilder.annotation.intercept.resource;

import com.github.osvaldopina.linkbuilder.annotation.linkcreator.LinkAnnotationCreator;
import com.github.osvaldopina.linkbuilder.annotation.linkcreator.LinkAnnotationCreatorRegistry;
import com.github.osvaldopina.linkbuilder.fromcall.MethodCall;
import com.github.osvaldopina.linkbuilder.fromcall.MethodCallFactory;
import org.easymock.EasyMockRule;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;

import java.lang.reflect.Method;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;



public class AnnotatedLinksResourceInterceptorTest extends EasyMockSupport {

	@Rule
	public EasyMockRule mocks = new EasyMockRule(this);

	@Mock
	private LinkAnnotationCreatorRegistry linkCreatorRegistry;

	@Mock
	private LinkAnnotationCreator linkAnnotationCreator;

	@Mock
	private MethodCallFactory methodCallFactory;

	@Mock
	MethodCall methodCall;

	Object returnValue = new Object();

	Method method = Object.class.getMethod("toString");

	Object[] args = {};

	Object target = new Object();


	@TestSubject
	AnnotatedLinksResourceInterceptor annotatedLinksResourceInterceptor = new AnnotatedLinksResourceInterceptor(null, null);


	public AnnotatedLinksResourceInterceptorTest() throws Exception {
	}

	@Test
	public void afterReturning_nonNullReturnValue() throws Throwable {
		expect(methodCallFactory.create(method, args)).andReturn(methodCall);
		expect(linkCreatorRegistry.get(returnValue)).andReturn(linkAnnotationCreator);
		linkAnnotationCreator.createAndSetForResourceAnnotations(methodCall, returnValue);
		expectLastCall();

		replayAll();

		annotatedLinksResourceInterceptor.afterReturning(returnValue, method, args, target);

		verifyAll();
	}

	@Test
	public void afterReturning_nullReturnValue() throws Throwable {

		replayAll();

		annotatedLinksResourceInterceptor.afterReturning(null, method, args, target);

		verifyAll();
	}

}