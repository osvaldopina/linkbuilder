package com.github.osvaldopina.linkbuilder.fromcall.currentcallrecorder.impl;

import java.lang.reflect.Method;

import com.github.osvaldopina.linkbuilder.utils.IntrospectionUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class CurrentCallBeanPostProcessor implements BeanPostProcessor, ApplicationContextAware {

	private IntrospectionUtils introspectionUtils;

	private CurrentCallRecorderMethodInterceptorCreator currentCallRecorderMethodInterceptorCreator =
			CurrentCallRecorderMethodInterceptorCreator.INSTANCE;

	private ApplicationContext applicationContext;

	public CurrentCallBeanPostProcessor(IntrospectionUtils introspectionUtils) {

		this.introspectionUtils = introspectionUtils;
	}

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		if (introspectionUtils.isRestController(bean)) {
			for (Method method : introspectionUtils.getEnableSelfFromCurrentCallAnnotatedMethods(bean)) {
				return currentCallRecorderMethodInterceptorCreator.
						addInterceptorToMethods(bean, applicationContext);
			}
		}
		return bean;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
}
