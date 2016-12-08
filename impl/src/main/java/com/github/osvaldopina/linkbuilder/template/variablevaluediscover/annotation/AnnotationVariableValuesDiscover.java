package com.github.osvaldopina.linkbuilder.template.variablevaluediscover.annotation;

import com.github.osvaldopina.linkbuilder.LinkProperties;
import com.github.osvaldopina.linkbuilder.annotation.reader.properties.LinkAnnotationProperties;
import com.github.osvaldopina.linkbuilder.fromcall.MethodCall;
import com.github.osvaldopina.linkbuilder.template.VariableValues;
import com.github.osvaldopina.linkbuilder.template.Variables;
import com.github.osvaldopina.linkbuilder.template.conditionalsubustitution.ConditionalVariableSubstituionStrategies;

import java.util.List;

public interface AnnotationVariableValuesDiscover {

    VariableValues getVariableValues(
            Variables variables, MethodCall currentMethod, Object payload,
            LinkAnnotationProperties linkAnnotationProperties);
}
