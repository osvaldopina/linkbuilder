package com.github.osvaldopina.linkbuilder.urigeneration.link.annotation.impl;

import java.lang.reflect.Method;

import com.github.osvaldopina.linkbuilder.annotation.reader.properties.LinkAnnotationProperties;
import com.github.osvaldopina.linkbuilder.fromcall.MethodCall;
import com.github.osvaldopina.linkbuilder.linkdestination.LinkDestinationRegistry;
import com.github.osvaldopina.linkbuilder.template.Template;
import com.github.osvaldopina.linkbuilder.template.TemplateRegistry;
import com.github.osvaldopina.linkbuilder.template.VariableValues;
import com.github.osvaldopina.linkbuilder.template.variablevaluediscover.annotation.AnnotationVariableValuesDiscover;
import com.github.osvaldopina.linkbuilder.urigeneration.base.BaseUriDiscover;
import com.github.osvaldopina.linkbuilder.urigeneration.link.annotation.AnnotationUriGenerator;
import com.github.osvaldopina.linkbuilder.utils.UrlPathContatenator;

public class AnnotationUriGeneratorImpl implements AnnotationUriGenerator {

	private LinkDestinationRegistry linkDestinationRegistry;

	private TemplateRegistry templateRegistry;

	private AnnotationVariableValuesDiscover annotationVariableValuesDiscover;

	private BaseUriDiscover baseUriDiscover;

	private UrlPathContatenator urlPathContatenator = UrlPathContatenator.INSTANCE;


	public AnnotationUriGeneratorImpl(LinkDestinationRegistry linkDestinationRegistry, TemplateRegistry templateRegistry,
									  AnnotationVariableValuesDiscover annotationVariableValuesDiscover,
									  BaseUriDiscover baseUriDiscover) {
		this.linkDestinationRegistry = linkDestinationRegistry;
		this.templateRegistry = templateRegistry;
		this.annotationVariableValuesDiscover = annotationVariableValuesDiscover;
		this.baseUriDiscover = baseUriDiscover;
	}


	@Override
	public String generateUri(LinkAnnotationProperties linkAnnotationProperties, MethodCall currentMethodCall, Object resource) {

		Method destination = linkDestinationRegistry.getTemplatedMethod(linkAnnotationProperties.getDestination());

		Template template = templateRegistry.getTemplate(destination);

		VariableValues variableValues = annotationVariableValuesDiscover.getVariableValues(template.getVariables(),
				currentMethodCall, resource, linkAnnotationProperties);

		if (linkAnnotationProperties.isTemplated()) {
			return urlPathContatenator.concat(baseUriDiscover.getBaseUri(), template.toTemplatedUri(variableValues));
		} else {
			return urlPathContatenator.concat(baseUriDiscover.getBaseUri(), template.toUri(variableValues));
		}
	}
}
