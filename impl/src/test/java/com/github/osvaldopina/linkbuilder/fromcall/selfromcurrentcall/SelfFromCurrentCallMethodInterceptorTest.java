package com.github.osvaldopina.linkbuilder.fromcall.selfromcurrentcall;

import static org.easymock.EasyMock.*;

import java.lang.reflect.Method;

import com.github.osvaldopina.linkbuilder.annotation.linkcreator.springhateoas.LinkCreatorForAnnotations;
import com.github.osvaldopina.linkbuilder.fromcall.MethodCall;
import com.github.osvaldopina.linkbuilder.fromcall.MethodCallFactory;
import com.github.osvaldopina.linkbuilder.urigeneration.link.methodcall.MethodCallUriGenerator;
import com.github.osvaldopina.linkbuilder.utils.IntrospectionUtils;
import org.easymock.EasyMockRule;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.context.ApplicationContext;

public class SelfFromCurrentCallMethodInterceptorTest extends EasyMockSupport {

	@Rule
	public EasyMockRule mocks = new EasyMockRule(this);

	@Mock
	private MethodCallUriGenerator methodCallUriGenerator;

	@Mock
	private IntrospectionUtils introspectionUtils;

	@Mock
	private MethodCallFactory methodCallFactory;

	@Mock
	private LinkCreatorForAnnotations linkCreatorForAnnotations;


	private Object[] params = new Object[] { new Object()} ;

	@Mock
	private MethodCall methodCall;

	private Object target = new Object();

	private Method method = Object.class.getMethods()[0];

	private Object returnValue = new Object();

	@TestSubject
	private SelfFromCurrentCallMethodInterceptor selfFromCurrentCallMethodInterceptor = new
			SelfFromCurrentCallMethodInterceptor(methodCallUriGenerator, introspectionUtils);



	@Test
	public void afterReturning_nullReturnValue() throws Throwable {

		replayAll();

		selfFromCurrentCallMethodInterceptor.afterReturning(null, method, params, target);

		verifyAll();

	}

	@Test
	public void afterReturning() throws Throwable {
		expect(methodCallFactory.create(method, params)).andReturn(methodCall);
		linkCreatorForAnnotations.createAndSetSelfLinkIfNeeded(methodCallUriGenerator, introspectionUtils, methodCall, returnValue);
		expectLastCall();

		replayAll();

		selfFromCurrentCallMethodInterceptor.afterReturning(returnValue, method, params, target);

		verifyAll();

	}

}