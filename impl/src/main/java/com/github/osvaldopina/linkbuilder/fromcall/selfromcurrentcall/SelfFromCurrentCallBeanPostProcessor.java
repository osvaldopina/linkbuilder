package com.github.osvaldopina.linkbuilder.fromcall.selfromcurrentcall;

import java.lang.reflect.Method;

import com.github.osvaldopina.linkbuilder.annotation.SelfFromCurrentCall;
import com.github.osvaldopina.linkbuilder.urigeneration.link.methodcall.MethodCallUriGenerator;
import com.github.osvaldopina.linkbuilder.utils.IntrospectionUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

public class SelfFromCurrentCallBeanPostProcessor implements BeanPostProcessor {

	private MethodCallUriGenerator methodCallUriGenerator;

	private IntrospectionUtils introspectionUtils;

	private SelfFromCurrentCallMethodInterceptorCreator selfFromCurrentCallMethodInterceptorCreator =
			SelfFromCurrentCallMethodInterceptorCreator.INSTANCE;


	public SelfFromCurrentCallBeanPostProcessor(MethodCallUriGenerator methodCallUriGenerator,
												IntrospectionUtils introspectionUtils) {

		this.methodCallUriGenerator = methodCallUriGenerator;
		this.introspectionUtils = introspectionUtils;

	}

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		if (introspectionUtils.isRestController(bean)) {
			for (Method method : introspectionUtils.getAnnotatedMethods(bean, SelfFromCurrentCall.class)) {
				return selfFromCurrentCallMethodInterceptorCreator.
						addInterceptorToMethods(bean, methodCallUriGenerator, introspectionUtils);
			}
		}
		return bean;
	}

}
