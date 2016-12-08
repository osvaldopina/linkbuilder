package com.github.osvaldopina.linkbuilder.fromcall.currentcallrecorder.impl;

import static org.easymock.EasyMock.*;

import java.lang.reflect.Method;

import com.github.osvaldopina.linkbuilder.fromcall.MethodCall;
import com.github.osvaldopina.linkbuilder.fromcall.MethodCallFactory;
import org.easymock.EasyMockRule;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.context.ApplicationContext;

public class CurrentCallRecorderMethodInterceptorTest extends EasyMockSupport {

	@Rule
	public EasyMockRule mocks = new EasyMockRule(this);

	@Mock
	ApplicationContext applicationContext;

	@Mock
	MethodCallFactory methodCallFactory;

	@Mock
	CurrentCall currentCall;

	Object[] params = new Object[] { new Object()} ;

	@Mock
	MethodCall methodCall;

	Object target = new Object();

	Method method = Object.class.getMethods()[0];

	@TestSubject
	CurrentCallRecorderMethodInterceptor currentCallRecorderMethodInterceptor =
			new CurrentCallRecorderMethodInterceptor(applicationContext);


	@Test
	public void before() throws Throwable {
		expect(applicationContext.getBean(CurrentCall.class)).andReturn(currentCall);
		expect(methodCallFactory.create(method, params)).andReturn(methodCall);
		currentCall.setMethodCall(methodCall);
		expectLastCall();

		replayAll();

		currentCallRecorderMethodInterceptor.before(method, params, target);

		verifyAll();

	}
}