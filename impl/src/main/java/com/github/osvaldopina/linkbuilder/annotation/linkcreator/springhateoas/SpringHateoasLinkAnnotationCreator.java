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
	public void createAndSetForMethodAnnotations(MethodCall methodCall, Object resource) {

		List<LinkAnnotationProperties> linksProperties = linkAnnotationReader.read(methodCall.getMethod());

		for (LinkAnnotationProperties linkProperties : linksProperties) {
			createAndSetForMethodAnnotations(linkProperties, methodCall, resource);
		}

		createAndSetSelfLinkIfNeeded(methodCall, resource);
	}

	@Override
	public void createAndSetForResourceAnnotations(MethodCall methodCall, Object resource) {

		List<LinkAnnotationProperties> linksProperties = linkAnnotationReader.read(resource.getClass());

		LinkAnnotationCreator linkAnnotationCreator = null;

		for (LinkAnnotationProperties linkProperties : linksProperties) {
			createAndSetForMethodAnnotations(linkProperties, methodCall, resource);
		}
	}

	@Override
	public boolean canCreate(Object resource) {
		return resource != null && introspectionUtils.
				hasComposedAnnotation(resource.getClass(), com.github.osvaldopina.linkbuilder.annotation.Links.class);
	}

	private void createAndSetForMethodAnnotations(LinkAnnotationProperties linkAnnotationProperties, MethodCall currentMethodCall, Object resource) {
		if (resource instanceof ResourceSupport) {
			String linkUri = annotationUriGenerator.generateUri(linkAnnotationProperties, currentMethodCall, resource);
			((ResourceSupport) resource).add(new Link(linkUri, linkAnnotationProperties.getRel()));
		} else {
			throw new LinkBuilderException("Can only set link to instances of ResourceSupport but resource is " + resource.getClass());
		}
	}

	private void createAndSetSelfLinkIfNeeded(MethodCall currentMethodCall, Object resource) {
		if (introspectionUtils.isEnableSelfFromCurrentCallMethod(currentMethodCall.getMethod())) {
			if (resource instanceof ResourceSupport) {
				((ResourceSupport) resource).add(
						new Link(methodCallUriGenerator.generateUri(currentMethodCall, resource), "self"));
			} else {
				throw new LinkBuilderException("Can only set link to instances of ResourceSupport but resource is " + resource.getClass());
			}
		}
	}
}
