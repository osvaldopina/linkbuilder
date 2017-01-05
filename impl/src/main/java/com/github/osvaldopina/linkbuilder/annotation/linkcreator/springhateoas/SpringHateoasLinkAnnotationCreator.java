package com.github.osvaldopina.linkbuilder.annotation.linkcreator.springhateoas;

import java.lang.reflect.Method;
import java.util.List;

import com.github.osvaldopina.linkbuilder.LinkBuilderException;
import com.github.osvaldopina.linkbuilder.annotation.linkcreator.LinkAnnotationCreator;
import com.github.osvaldopina.linkbuilder.annotation.reader.impl.LinkAnnotationReader;
import com.github.osvaldopina.linkbuilder.annotation.reader.properties.LinkAnnotationProperties;
import com.github.osvaldopina.linkbuilder.fromcall.MethodCall;
import com.github.osvaldopina.linkbuilder.urigeneration.base.BaseUriDiscover;
import com.github.osvaldopina.linkbuilder.urigeneration.link.annotation.AnnotationUriGenerator;
import com.github.osvaldopina.linkbuilder.urigeneration.link.methodcall.MethodCallUriGenerator;
import com.github.osvaldopina.linkbuilder.utils.IntrospectionUtils;
import com.github.osvaldopina.linkbuilder.utils.UrlPathContatenator;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;

public class SpringHateoasLinkAnnotationCreator implements LinkAnnotationCreator {


	private BaseUriDiscover baseUriDiscover;

	private AnnotationUriGenerator annotationUriGenerator;

	private IntrospectionUtils introspectionUtils;

	private MethodCallUriGenerator methodCallUriGenerator;

	private UrlPathContatenator urlPathContatenator = UrlPathContatenator.INSTANCE;

	private LinkAnnotationReader linkAnnotationReader;

	public SpringHateoasLinkAnnotationCreator(BaseUriDiscover baseUriDiscover,
											  AnnotationUriGenerator annotationUriGenerator,
											  IntrospectionUtils introspectionUtils,
											  MethodCallUriGenerator methodCallUriGenerator,
											  LinkAnnotationReader linkAnnotationReader) {

		this.baseUriDiscover = baseUriDiscover;
		this.annotationUriGenerator = annotationUriGenerator;
		this.introspectionUtils = introspectionUtils;
		this.methodCallUriGenerator = methodCallUriGenerator;
		this.linkAnnotationReader = linkAnnotationReader;
	}

	@Override
	public boolean canCreate(Method method) {
		return introspectionUtils.
				hasComposedAnnotation(method, com.github.osvaldopina.linkbuilder.annotation.Links.class);
	}

	@Override
	public void createAndSetForMethodAnnotations(MethodCall methodCall, Object payload) {

		List<LinkAnnotationProperties> linksProperties = linkAnnotationReader.read(methodCall.getMethod());

		for (LinkAnnotationProperties linkProperties : linksProperties) {
			createAndSetForMethodAnnotations(linkProperties, methodCall, payload);
		}

		createAndSetSelfLinkIfNeeded(methodCall, payload);
	}

	@Override
	public void createAndSetForResourceAnnotations(MethodCall methodCall, Object payload) {

		List<LinkAnnotationProperties> linksProperties = linkAnnotationReader.read(payload.getClass());

		LinkAnnotationCreator linkAnnotationCreator = null;

		for (LinkAnnotationProperties linkProperties : linksProperties) {
			createAndSetForMethodAnnotations(linkProperties, methodCall, payload);
		}
	}

	@Override
	public boolean canCreate(Object payload) {
		return payload != null && introspectionUtils.
				hasComposedAnnotation(payload.getClass(), com.github.osvaldopina.linkbuilder.annotation.Links.class);
	}

	private void createAndSetForMethodAnnotations(LinkAnnotationProperties linkAnnotationProperties, MethodCall currentMethodCall, Object payload) {
		if (payload instanceof ResourceSupport) {
			String baseUri = baseUriDiscover.getBaseUri();
			String linkUri = annotationUriGenerator.generateUri(linkAnnotationProperties, currentMethodCall, payload);
			((ResourceSupport) payload).add(new Link(urlPathContatenator.concat(baseUri, linkUri), linkAnnotationProperties.getRel()));
		} else {
			throw new LinkBuilderException("Can only set link to instances of ResourceSupport by pyaload is " + payload.getClass());
		}
	}

	private void createAndSetSelfLinkIfNeeded(MethodCall currentMethodCall, Object payload) {
		if (introspectionUtils.isEnableSelfFromCurrentCallMethod(currentMethodCall.getMethod())) {
			if (payload instanceof ResourceSupport) {
				((ResourceSupport) payload).add(
						new Link(methodCallUriGenerator.generateUri(currentMethodCall, payload), "self"));
			} else {
				throw new LinkBuilderException("Can only set link to instances of ResourceSupport by pyaload is " + payload.getClass());
			}
		}
	}
}
