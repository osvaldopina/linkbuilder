package com.github.osvaldopina.linkbuilder.annotation.intercept;

import java.lang.reflect.Method;
import java.util.List;

import com.github.osvaldopina.linkbuilder.annotation.reader.AnnotationReader;
import com.github.osvaldopina.linkbuilder.annotation.reader.AnnotationReaderRegistry;
import com.github.osvaldopina.linkbuilder.annotation.reader.properties.LinkAnnotationProperties;
import com.github.osvaldopina.linkbuilder.fromcall.MethodCall;
import com.github.osvaldopina.linkbuilder.fromcall.MethodCallFactory;
import com.github.osvaldopina.linkbuilder.linkcreator.annotation.LinkAnnotationCreator;
import com.github.osvaldopina.linkbuilder.linkcreator.annotation.LinkAnnotationCreatorRegistry;
import org.springframework.aop.AfterReturningAdvice;

class AnnotatedLinksMethodInterceptor implements AfterReturningAdvice {


	private AnnotationReaderRegistry annotationReaderRegistry;
	private LinkAnnotationCreatorRegistry linkCreatorRegistry;

	private MethodCallFactory methodCallFactory = MethodCallFactory.INSTANCE;

	AnnotatedLinksMethodInterceptor(AnnotationReaderRegistry annotationReaderRegistry,
									LinkAnnotationCreatorRegistry linkCreatorRegistry) {

		this.annotationReaderRegistry = annotationReaderRegistry;
		this.linkCreatorRegistry = linkCreatorRegistry;
	}

	@Override
	public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {

		if (returnValue == null) {
			return;
		}

		AnnotationReader annotationReader = annotationReaderRegistry.get(method);

		if (annotationReader == null) {
			return;
		}

		List<LinkAnnotationProperties> linksProperties = annotationReader.read(method);

		LinkAnnotationCreator linkAnnotationCreator = null;
		MethodCall currentCall = methodCallFactory.create(method, args);

		for (LinkAnnotationProperties linkProperties : linksProperties) {
			linkAnnotationCreator = linkCreatorRegistry.get(linkProperties, returnValue);
			linkAnnotationCreator.createAndSet(linkProperties, currentCall, returnValue);
		}

		linkAnnotationCreator = getLinkCreatorForSelfLink(linkAnnotationCreator, currentCall, returnValue);

		if (linkAnnotationCreator != null) {
			linkAnnotationCreator.createAndSetSelfLinkIfNeeded(currentCall, returnValue);
		}
	}

	protected AnnotationReaderRegistry getAnnotationReaderRegistry() {
		return annotationReaderRegistry;
	}

	protected LinkAnnotationCreatorRegistry getLinkCreatorRegistry() {
		return linkCreatorRegistry;
	}

	private LinkAnnotationCreator getLinkCreatorForSelfLink(
			LinkAnnotationCreator lastLinkAnnotationCreator, MethodCall currentCall, Object returnValue) {

		if (canUseLastLinkCreator(returnValue, lastLinkAnnotationCreator, currentCall)) {
			return lastLinkAnnotationCreator;
		} else {
			return linkCreatorRegistry.get(currentCall, returnValue);
		}
	}

	private boolean canUseLastLinkCreator(Object returnValue, LinkAnnotationCreator linkAnnotationCreator, MethodCall methodCall) {
		return linkAnnotationCreator != null && linkAnnotationCreator.canCreate(methodCall, returnValue);
	}
}
