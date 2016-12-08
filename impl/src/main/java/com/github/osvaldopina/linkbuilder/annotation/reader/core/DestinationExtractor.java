package com.github.osvaldopina.linkbuilder.annotation.reader.core;

import static com.github.osvaldopina.linkbuilder.linkdestination.springhateoas.DestinationIdentityFactorty.*;

import java.lang.annotation.Annotation;

import com.github.osvaldopina.linkbuilder.LinkBuilderException;
import com.github.osvaldopina.linkbuilder.linkdestination.springhateoas.DestinationIdentityFactorty;
import com.github.osvaldopina.linkbuilder.utils.ReflectionUtils;
import com.github.osvaldopina.linkbuilder.utils.Utils;

public class DestinationExtractor {

	public static final DestinationExtractor INSTANCE = new DestinationExtractor();

	private ReflectionUtils reflectionUtils = ReflectionUtils.INSTANCE;

	private DestinationIdentityFactorty destinationIdentityFactorty = DestinationIdentityFactorty.INSTANCE;

	private static final String DESTINATION_PROPERTY = "destination";

	private static final String CONTROLLER_PROPERTY = "controller";

	private static final String REL_PROPERTY = "rel";


	protected DestinationExtractor() {
	}

	public String extract(Annotation annotation) {
		if (reflectionUtils.hasValue(annotation, DESTINATION_PROPERTY)) {
			if (reflectionUtils.hasValue(annotation, CONTROLLER_PROPERTY) || reflectionUtils.hasValue(annotation, REL_PROPERTY)) {
				throw new LinkBuilderException("You have to use either destination or controller and rel.");
			}
			Object destination = reflectionUtils.callMethod(Object.class, annotation, DESTINATION_PROPERTY);
			return destination.toString();
		} else {
			if (reflectionUtils.hasValue(annotation, CONTROLLER_PROPERTY) &&
					reflectionUtils.hasValue(annotation, REL_PROPERTY)) {
				Class<?> controller = reflectionUtils.callMethod(Class.class, annotation, CONTROLLER_PROPERTY);
				String rel = reflectionUtils.callMethod(String.class, annotation, REL_PROPERTY);
				return destinationIdentityFactorty.destination(controller, rel);
			}
			else {
				throw new LinkBuilderException("You have to use either destination or controller and rel.");
			}
		}
	}
}

