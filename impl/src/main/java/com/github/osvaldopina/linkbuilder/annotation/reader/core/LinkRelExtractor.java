package com.github.osvaldopina.linkbuilder.annotation.reader.core;

import com.github.osvaldopina.linkbuilder.LinkBuilderException;
import com.github.osvaldopina.linkbuilder.linkdestination.springhateoas.DestinationIdentityFactorty;
import com.github.osvaldopina.linkbuilder.utils.ReflectionUtils;
import com.github.osvaldopina.linkbuilder.utils.Utils;

import java.lang.annotation.Annotation;

public class LinkRelExtractor {

    public static final LinkRelExtractor INSTANCE = new LinkRelExtractor();

    private ReflectionUtils reflectionUtils = ReflectionUtils.INSTANCE;

    private PropertyAsStringExtractor propertyAsStringExtractor = PropertyAsStringExtractor.INSTANCE;

    private Utils utils = Utils.INSTANCE;

    protected LinkRelExtractor() {
    }

    public String extract(Annotation annotation) {

        String value = propertyAsStringExtractor.extract(annotation, "overridedRel");

        if (value != null) {
            return value;
        }

        value = propertyAsStringExtractor.extract(annotation, "rel");

        if (value != null) {
            return value;
        }

        if (reflectionUtils.hasMethod(annotation, "destination")) {
            Object destination = reflectionUtils.callMethod(Object.class, annotation, "destination");

            if (reflectionUtils.hasMethod(destination, "getRel")) {
                Object rel = reflectionUtils.callMethod(Object.class, destination, "getRel");
                if (rel != null) {
                    return rel.toString();
                }
            }

            if (utils.isPresent(destination)) {
                return destination.toString();
            }
        }

        throw new LinkBuilderException("Could not determine link rel");
    }


}
