package com.github.osvaldopina.linkbuilder.annotation.intercept.resource;

import java.lang.reflect.Method;
import java.util.List;

import com.github.osvaldopina.linkbuilder.annotation.reader.AnnotationReader;
import com.github.osvaldopina.linkbuilder.annotation.reader.AnnotationReaderRegistry;
import com.github.osvaldopina.linkbuilder.annotation.reader.properties.LinkAnnotationProperties;
import com.github.osvaldopina.linkbuilder.fromcall.MethodCall;
import com.github.osvaldopina.linkbuilder.fromcall.MethodCallFactory;
import com.github.osvaldopina.linkbuilder.annotation.linkcreator.LinkAnnotationCreator;
import com.github.osvaldopina.linkbuilder.annotation.linkcreator.LinkAnnotationCreatorRegistry;
import org.springframework.aop.AfterReturningAdvice;

class AnnotatedLinksResourceInterceptor implements AfterReturningAdvice {



	private LinkAnnotationCreatorRegistry linkCreatorRegistry;

	private MethodCallFactory methodCallFactory = MethodCallFactory.INSTANCE;

	AnnotatedLinksResourceInterceptor(AnnotationReaderRegistry annotationReaderRegistry,
									LinkAnnotationCreatorRegistry linkCreatorRegistry) {


		this.linkCreatorRegistry = linkCreatorRegistry;
	}

	@Override
	public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {

		if (returnValue == null) {
			return;
		}

		linkCreatorRegistry.get(returnValue).
				createAndSetForResourceAnnotations(methodCallFactory.create(method, args), returnValue);
	}

}
