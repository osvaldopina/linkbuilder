package com.github.osvaldopina.linkbuilder.annotation.intercept;

import com.github.osvaldopina.linkbuilder.annotation.reader.AnnotationReaderRegistry;
import com.github.osvaldopina.linkbuilder.linkcreator.annotation.LinkAnnotationCreatorRegistry;
import org.springframework.aop.framework.ProxyFactory;

class AnnotatedLinksMethodInterceptorCreator {

	public static final AnnotatedLinksMethodInterceptorCreator INSTANCE = new AnnotatedLinksMethodInterceptorCreator();

	AnnotatedLinksMethodInterceptorCreator() {
	}

	Object addInterceptorToMethods(Object object, AnnotationReaderRegistry annotationReaderRegistry,
								   LinkAnnotationCreatorRegistry linkAnnotationCreatorRegistry) {
		ProxyFactory factory = new ProxyFactory();
		factory.addAdvice(new AnnotatedLinksMethodInterceptor(annotationReaderRegistry, linkAnnotationCreatorRegistry));
		factory.setTarget(object);
		return factory.getProxy();
	}
}
