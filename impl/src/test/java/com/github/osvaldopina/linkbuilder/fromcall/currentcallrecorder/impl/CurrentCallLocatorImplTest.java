package com.github.osvaldopina.linkbuilder.fromcall.currentcallrecorder.impl;

import static org.easymock.EasyMock.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import com.github.osvaldopina.linkbuilder.fromcall.MethodCall;
import com.github.osvaldopina.linkbuilder.utils.IntrospectionUtils;
import org.easymock.EasyMockRule;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.context.ApplicationContext;

public class CurrentCallLocatorImplTest extends EasyMockSupport {

	@Rule
	public EasyMockRule mocks = new EasyMockRule(this);

	@Mock
	private ApplicationContext applicationContext;

	@Mock
	private CurrentCall currentCall;

	@Mock
	private MethodCall methodCall;

	@TestSubject
	private CurrentCallLocatorImpl currentCallLocatorImpl = new CurrentCallLocatorImpl();


	@Test
	public void getCurrentCall() throws Exception {
		expect(applicationContext.getBean(CurrentCall.class)).andReturn(currentCall);
		expect(currentCall.getMethodCall()).andReturn(methodCall);

		replayAll();

		assertThat(currentCallLocatorImpl.getCurrentCall(), is(sameInstance(methodCall)));

		verifyAll();

	}
}