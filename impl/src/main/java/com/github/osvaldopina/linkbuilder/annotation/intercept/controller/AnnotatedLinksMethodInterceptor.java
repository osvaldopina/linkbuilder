package com.github.osvaldopina.linkbuilder.annotation.intercept.controller;

import java.lang.reflect.Method;

import com.github.osvaldopina.linkbuilder.annotation.reader.AnnotationReaderRegistry;
import com.github.osvaldopina.linkbuilder.fromcall.MethodCallFactory;
import com.github.osvaldopina.linkbuilder.annotation.linkcreator.LinkAnnotationCreatorRegistry;
import org.springframework.aop.AfterReturningAdvice;

class AnnotatedLinksMethodInterceptor implements AfterReturningAdvice {


	private LinkAnnotationCreatorRegistry linkCreatorRegistry;

	private MethodCallFactory methodCallFactory = MethodCallFactory.INSTANCE;

	AnnotatedLinksMethodInterceptor(AnnotationReaderRegistry annotationReaderRegistry,
									LinkAnnotationCreatorRegistry linkCreatorRegistry) {

		this.linkCreatorRegistry = linkCreatorRegistry;
	}

	@Override
	public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {

		if (returnValue == null) {
			return;
		}

		linkCreatorRegistry.get(method).
				createAndSetForMethodAnnotations(methodCallFactory.create(method, args), returnValue);
	}

}
