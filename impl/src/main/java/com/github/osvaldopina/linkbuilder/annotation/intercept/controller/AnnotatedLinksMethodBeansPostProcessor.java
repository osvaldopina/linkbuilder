package com.github.osvaldopina.linkbuilder.annotation.intercept.controller;

import java.lang.reflect.Method;

import com.github.osvaldopina.linkbuilder.annotation.reader.AnnotationReaderRegistry;
import com.github.osvaldopina.linkbuilder.annotation.linkcreator.LinkAnnotationCreatorRegistry;
import com.github.osvaldopina.linkbuilder.utils.IntrospectionUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

public class AnnotatedLinksMethodBeansPostProcessor implements BeanPostProcessor {

	private AnnotationReaderRegistry annotationReaderRegistry;
	private LinkAnnotationCreatorRegistry linkAnnotationCreatorRegistry;
	private IntrospectionUtils introspectionUtils;

	private AnnotatedLinksMethodInterceptorCreator interceptorCreator = AnnotatedLinksMethodInterceptorCreator.INSTANCE;

	public AnnotatedLinksMethodBeansPostProcessor(
			LinkAnnotationCreatorRegistry linkAnnotationCreatorRegistry,
			IntrospectionUtils introspectionUtils,
			AnnotationReaderRegistry annotationReaderRegistry) {

		this.annotationReaderRegistry = annotationReaderRegistry;
		this.linkAnnotationCreatorRegistry = linkAnnotationCreatorRegistry;
		this.introspectionUtils = introspectionUtils;
	}

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		if (introspectionUtils.isRestController(bean)) {
			for (Method method : bean.getClass().getMethods()) {
				if (annotationReaderRegistry.get(method) != null) {
					return interceptorCreator.addInterceptorToMethods(bean, linkAnnotationCreatorRegistry);
				}
			}
		}
		return bean;
	}
}
