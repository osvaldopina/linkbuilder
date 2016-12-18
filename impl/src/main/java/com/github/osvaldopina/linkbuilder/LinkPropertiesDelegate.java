package com.github.osvaldopina.linkbuilder;

import com.github.osvaldopina.linkbuilder.fromcall.MethodCall;
import com.github.osvaldopina.linkbuilder.template.conditionalsubustitution.ConditionalVariableSubstitutionStrategies;
import com.github.osvaldopina.linkbuilder.template.conditionalsubustitution.ConditionalVariableSubstitutionStrategy;

public class LinkPropertiesDelegate implements LinkProperties {

    private LinkProperties linkProperties;

    public LinkPropertiesDelegate(LinkProperties linkProperties) {
        this.linkProperties = linkProperties;
    }

    @Override
    public String getRel() {
        return linkProperties.getRel();
    }

    @Override
    public void setRel(String rel) {
        linkProperties.setRel(rel);
    }

    @Override
    public String getWhenExpression() {
        return linkProperties.getWhenExpression();
    }

    @Override
    public void setWhenExpression(String whenExpression) {

    }

    @Override
    public Object getPayload() {
        return linkProperties.getPayload();
    }

    @Override
    public void setPayload(Object payload) {

    }

    @Override
    public MethodCall getMethodCall() {
        return linkProperties.getMethodCall();
    }

    @Override
    public void setMethodCall(MethodCall methodCall) {
        linkProperties.setMethodCall(methodCall);
    }

    @Override
    public ConditionalVariableSubstitutionStrategies getConditionalVariableSubstituionStrategies() {
        return linkProperties.getConditionalVariableSubstituionStrategies();
    }

    @Override
    public void addConditionalVariableSubstitutionStrategy(ConditionalVariableSubstitutionStrategy conditionalVariableSubstitutionStrategy) {
        linkProperties.addConditionalVariableSubstitutionStrategy(conditionalVariableSubstitutionStrategy);
    }

    @Override
    public boolean isTemplated() {
        return linkProperties.isTemplated();
    }

    @Override
    public void setTemplated(boolean templated) {
        linkProperties.setTemplated(templated);
    }
}
