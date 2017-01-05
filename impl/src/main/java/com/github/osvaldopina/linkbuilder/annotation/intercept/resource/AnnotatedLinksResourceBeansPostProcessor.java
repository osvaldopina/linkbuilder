package com.github.osvaldopina.linkbuilder.annotation.intercept.resource;

import java.lang.reflect.Method;

import com.github.osvaldopina.linkbuilder.annotation.reader.AnnotationReaderRegistry;
import com.github.osvaldopina.linkbuilder.annotation.linkcreator.LinkAnnotationCreatorRegistry;
import com.github.osvaldopina.linkbuilder.utils.IntrospectionUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

public class AnnotatedLinksResourceBeansPostProcessor implements BeanPostProcessor {

	private AnnotationReaderRegistry annotationReaderRegistry;
	private LinkAnnotationCreatorRegistry linkAnnotationCreatorRegistry;
	private IntrospectionUtils introspectionUtils;

	private AnnotatedLinksResourceInterceptorCreator interceptorCreator = AnnotatedLinksResourceInterceptorCreator.INSTANCE;

	public AnnotatedLinksResourceBeansPostProcessor(
			AnnotationReaderRegistry annotationReaderRegistry,
			LinkAnnotationCreatorRegistry linkAnnotationCreatorRegistry,
			IntrospectionUtils introspectionUtils) {

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
				if (annotationReaderRegistry.get(method.getReturnType()) != null) {
					return interceptorCreator.addInterceptorToMethods(bean, annotationReaderRegistry, linkAnnotationCreatorRegistry);
				}
			}
		}
		return bean;
	}
}
