package com.github.osvaldopina.linkbuilder.annotation.linkcreator.springhateoas;

import com.github.osvaldopina.linkbuilder.LinkBuilderException;
import com.github.osvaldopina.linkbuilder.annotation.reader.properties.LinkAnnotationProperties;
import com.github.osvaldopina.linkbuilder.expression.ExpressionExecutor;
import com.github.osvaldopina.linkbuilder.fromcall.MethodCall;
import com.github.osvaldopina.linkbuilder.urigeneration.link.annotation.AnnotationUriGenerator;
import com.github.osvaldopina.linkbuilder.urigeneration.link.methodcall.MethodCallUriGenerator;
import com.github.osvaldopina.linkbuilder.utils.IntrospectionUtils;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;

public class LinkCreatorForAnnotations {

	public static final LinkCreatorForAnnotations INSTANCE = new LinkCreatorForAnnotations();

	LinkCreatorForAnnotations() {

	}

	public void createAndSetForAnnotations(ExpressionExecutor expressionExecutor,
										   AnnotationUriGenerator annotationUriGenerator,
										   LinkAnnotationProperties linkAnnotationProperties,
										   MethodCall currentMethodCall, Object resource) {

		if (resource instanceof ResourceSupport) {
			if (whenExpressionNotInformedOrTrue(expressionExecutor, linkAnnotationProperties.getWhen(), resource, currentMethodCall.getParams())) {
				String linkUri = annotationUriGenerator.generateUri(linkAnnotationProperties, currentMethodCall, resource);
				((ResourceSupport) resource).add(new Link(linkUri, linkAnnotationProperties.getRel()));
			}
		} else {
			throw new LinkBuilderException("Can only set link to instances of ResourceSupport but resource is " + resource.getClass());
		}
	}

	private boolean whenExpressionNotInformedOrTrue(ExpressionExecutor expressionExecutor, String when, Object resource, Object[] params) {
		return (when == null || (when != null && expressionExecutor.isTrue(when, resource, params)));
	}

	public void createAndSetSelfLinkIfNeeded(MethodCallUriGenerator methodCallUriGenerator,
											 IntrospectionUtils introspectionUtils, MethodCall currentMethodCall,
											 Object resource) {

		if (introspectionUtils.isEnableSelfFromCurrentCallMethod(currentMethodCall.getMethod())) {
			if (resource instanceof ResourceSupport) {
				((ResourceSupport) resource).add(
						new Link(methodCallUriGenerator.generateUri(currentMethodCall, resource, false), "self"));
			} else {
				throw new LinkBuilderException("Can only set link to instances of ResourceSupport but resource is " + resource.getClass());
			}
		}
	}
}