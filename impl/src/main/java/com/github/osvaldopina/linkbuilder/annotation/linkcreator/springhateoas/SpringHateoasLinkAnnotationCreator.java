package com.github.osvaldopina.linkbuilder.annotation.linkcreator.springhateoas;

import java.lang.reflect.Method;
import java.util.List;

import com.github.osvaldopina.linkbuilder.LinkBuilderException;
import com.github.osvaldopina.linkbuilder.annotation.linkcreator.LinkAnnotationCreator;
import com.github.osvaldopina.linkbuilder.annotation.reader.impl.LinkAnnotationReader;
import com.github.osvaldopina.linkbuilder.annotation.reader.properties.LinkAnnotationProperties;
import com.github.osvaldopina.linkbuilder.expression.ExpressionExecutor;
import com.github.osvaldopina.linkbuilder.fromcall.MethodCall;
import com.github.osvaldopina.linkbuilder.urigeneration.base.BaseUriDiscover;
import com.github.osvaldopina.linkbuilder.urigeneration.link.annotation.AnnotationUriGenerator;
import com.github.osvaldopina.linkbuilder.urigeneration.link.methodcall.MethodCallUriGenerator;
import com.github.osvaldopina.linkbuilder.utils.IntrospectionUtils;
import com.github.osvaldopina.linkbuilder.utils.UrlPathContatenator;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;

public class SpringHateoasLinkAnnotationCreator implements LinkAnnotationCreator {


	private ExpressionExecutor expressionExecutor;

	private AnnotationUriGenerator annotationUriGenerator;

	private IntrospectionUtils introspectionUtils;

	private LinkAnnotationReader linkAnnotationReader;

	private LinkCreatorForAnnotations linkCreatorForAnnotations = LinkCreatorForAnnotations.INSTANCE;

	public SpringHateoasLinkAnnotationCreator(ExpressionExecutor expressionExecutor,
											  AnnotationUriGenerator annotationUriGenerator,
											  IntrospectionUtils introspectionUtils,
											  LinkAnnotationReader linkAnnotationReader) {

		this.expressionExecutor = expressionExecutor;
		this.annotationUriGenerator = annotationUriGenerator;
		this.introspectionUtils = introspectionUtils;
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
			linkCreatorForAnnotations.createAndSetForAnnotations(expressionExecutor, annotationUriGenerator,
					linkProperties, methodCall, resource);
		}

	}

	@Override
	public void createAndSetForResourceAnnotations(MethodCall methodCall, Object resource) {

		List<LinkAnnotationProperties> linksProperties = linkAnnotationReader.read(resource.getClass());

		for (LinkAnnotationProperties linkProperties : linksProperties) {
			linkCreatorForAnnotations.createAndSetForAnnotations(expressionExecutor, annotationUriGenerator,
					linkProperties, methodCall, resource);
		}
	}

	@Override
	public boolean canCreate(Object resource) {
		return resource != null && resource instanceof ResourceSupport && introspectionUtils.
				hasComposedAnnotation(resource.getClass(), com.github.osvaldopina.linkbuilder.annotation.Links.class);
	}

}
