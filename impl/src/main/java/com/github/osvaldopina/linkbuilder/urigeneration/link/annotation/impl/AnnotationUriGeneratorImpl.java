package com.github.osvaldopina.linkbuilder.urigeneration.link.annotation.impl;

import com.github.osvaldopina.linkbuilder.annotation.reader.properties.LinkAnnotationProperties;
import com.github.osvaldopina.linkbuilder.fromcall.MethodCall;
import com.github.osvaldopina.linkbuilder.linkdestination.LinkDestinationRegistry;
import com.github.osvaldopina.linkbuilder.template.*;
import com.github.osvaldopina.linkbuilder.template.variablevaluediscover.annotation.AnnotationVariableValuesDiscover;
import com.github.osvaldopina.linkbuilder.urigeneration.link.annotation.AnnotationUriGenerator;

import java.lang.reflect.Method;

public class AnnotationUriGeneratorImpl implements AnnotationUriGenerator {

    private LinkDestinationRegistry linkDestinationRegistry;

    private TemplateRegistry templateRegistry;

    private AnnotationVariableValuesDiscover annotationVariableValuesDiscover;

    public AnnotationUriGeneratorImpl(LinkDestinationRegistry linkDestinationRegistry, TemplateRegistry templateRegistry, AnnotationVariableValuesDiscover annotationVariableValuesDiscover) {
        this.linkDestinationRegistry = linkDestinationRegistry;
        this.templateRegistry = templateRegistry;
        this.annotationVariableValuesDiscover = annotationVariableValuesDiscover;
    }


    @Override
    public String generateUri(LinkAnnotationProperties linkAnnotationProperties, MethodCall currentMethodCall, Object payload) {

        Method destination = linkDestinationRegistry.getTemplatedMethod(linkAnnotationProperties.getDestination());

        Template template = templateRegistry.getTemplate(destination);


       VariableValues variableValues = annotationVariableValuesDiscover.getVariableValues(template.getVariables(), currentMethodCall, payload, linkAnnotationProperties);

        if (linkAnnotationProperties.isTemplated()) {
            return template.toTemplatedUri(variableValues);
        }
        else {
            return template.toUri(variableValues);
        }
    }
}
