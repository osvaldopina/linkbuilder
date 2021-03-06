package com.github.osvaldopina.linkbuilder.annotation.intercept.controller;

import com.github.osvaldopina.linkbuilder.annotation.reader.AnnotationReaderRegistry;
import com.github.osvaldopina.linkbuilder.annotation.linkcreator.LinkAnnotationCreatorRegistry;
import org.springframework.aop.framework.ProxyFactory;

class AnnotatedLinksMethodInterceptorCreator {

	public static final AnnotatedLinksMethodInterceptorCreator INSTANCE = new AnnotatedLinksMethodInterceptorCreator();

	AnnotatedLinksMethodInterceptorCreator() {
	}

	Object addInterceptorToMethods(Object object, LinkAnnotationCreatorRegistry linkAnnotationCreatorRegistry) {
		ProxyFactory factory = new ProxyFactory();
		factory.addAdvice(new AnnotatedLinksMethodInterceptor(linkAnnotationCreatorRegistry));
		factory.setTarget(object);
		return factory.getProxy();
	}
}
