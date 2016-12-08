package com.github.osvaldopina.linkbuilder.urigeneration.link.methodcall.impl;

import com.github.osvaldopina.linkbuilder.urigeneration.base.BaseUriDiscover;
import com.github.osvaldopina.linkbuilder.fromcall.MethodCall;
import com.github.osvaldopina.linkbuilder.template.Template;
import com.github.osvaldopina.linkbuilder.template.TemplateRegistry;
import com.github.osvaldopina.linkbuilder.template.VariableValues;
import com.github.osvaldopina.linkbuilder.template.conditionalsubustitution.ConditionalVariableSubstituionStrategies;
import com.github.osvaldopina.linkbuilder.template.variablevaluediscover.methodcall.MethodCallVariableValuesDiscover;
import com.github.osvaldopina.linkbuilder.urigeneration.link.methodcall.MethodCallUriGenerator;
import com.github.osvaldopina.linkbuilder.utils.UrlPathContatenator;

public class MethodCallUriGeneratorImpl implements MethodCallUriGenerator {

    private TemplateRegistry templateRegistry;

    private BaseUriDiscover baseUriDiscover;

    private MethodCallVariableValuesDiscover methodCallVariableValuesDiscover;

    private UrlPathContatenator urlPathContatenator = UrlPathContatenator.INSTANCE;

    public MethodCallUriGeneratorImpl(TemplateRegistry templateRegistry,
                                      BaseUriDiscover baseUriDiscover,
                                      MethodCallVariableValuesDiscover methodCallVariableValuesDiscover) {

        this.templateRegistry = templateRegistry;
        this.baseUriDiscover = baseUriDiscover;
        this.methodCallVariableValuesDiscover = methodCallVariableValuesDiscover;
    }

    @Override
    public String generateUri(MethodCall methodCall, Object payload) {


        Template template = templateRegistry.getTemplate(methodCall.getMethod());

        return generateUri(methodCall, payload, new ConditionalVariableSubstituionStrategies());

    }

    @Override
    public String generateUri(MethodCall methodCall, Object payload,
                              ConditionalVariableSubstituionStrategies conditionalVariableSubstituionStrategies) {

        Template template = templateRegistry.getTemplate(methodCall.getMethod());

        VariableValues variableValues = methodCallVariableValuesDiscover.getVariableValues(
                template.getVariables(), methodCall, payload, conditionalVariableSubstituionStrategies);

        return urlPathContatenator.concat(baseUriDiscover.getBaseUri(), template.toUri(variableValues));

    }
}
