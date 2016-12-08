package com.github.osvaldopina.linkbuilder.fromcall;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

import java.lang.reflect.Method;

import org.easymock.EasyMockRule;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.context.ApplicationContext;

public class MethodCallFactoryTest {


	private Method method = Object.class.getMethods()[0];

	private Object[] params = new Object[] { new Object() };

	private MethodCallFactory methodCallFactory = new MethodCallFactory();


	@Test
	public void create() throws Exception {

		MethodCall methodCall = methodCallFactory.create(method, params);

		assertThat(methodCall, is(notNullValue()));
		assertThat(methodCall.getMethod(), is(sameInstance(method)));
		assertThat(methodCall.getParams(), is(sameInstance(params)));



	}
}