package com.github.osvaldopina.linkbuilder.fromcall.currentcallrecorder.impl;

import org.springframework.aop.framework.ProxyFactory;
import org.springframework.context.ApplicationContext;

public class CurrentCallRecorderMethodInterceptorCreator {

	public static final CurrentCallRecorderMethodInterceptorCreator INSTANCE
			= new CurrentCallRecorderMethodInterceptorCreator();

	CurrentCallRecorderMethodInterceptorCreator() {

	}

	public Object addInterceptorToMethods(Object object, ApplicationContext applicationContext) {
		ProxyFactory factory = new ProxyFactory();
		factory.addAdvice(new CurrentCallRecorderMethodInterceptor(applicationContext));
		factory.setTarget(object);
		return factory.getProxy();

	}

}
