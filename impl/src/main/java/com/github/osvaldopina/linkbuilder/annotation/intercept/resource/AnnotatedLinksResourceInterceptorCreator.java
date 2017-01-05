package com.github.osvaldopina.linkbuilder.annotation.intercept.resource;

import com.github.osvaldopina.linkbuilder.annotation.reader.AnnotationReaderRegistry;
import com.github.osvaldopina.linkbuilder.annotation.linkcreator.LinkAnnotationCreatorRegistry;
import org.springframework.aop.framework.ProxyFactory;

class AnnotatedLinksResourceInterceptorCreator {

	public static final AnnotatedLinksResourceInterceptorCreator INSTANCE = new AnnotatedLinksResourceInterceptorCreator();

	AnnotatedLinksResourceInterceptorCreator() {
	}

	Object addInterceptorToMethods(Object object, AnnotationReaderRegistry annotationReaderRegistry,
								   LinkAnnotationCreatorRegistry linkAnnotationCreatorRegistry) {
		ProxyFactory factory = new ProxyFactory();
		factory.addAdvice(new AnnotatedLinksResourceInterceptor(annotationReaderRegistry, linkAnnotationCreatorRegistry));
		factory.setTarget(object);
		return factory.getProxy();
	}
}
