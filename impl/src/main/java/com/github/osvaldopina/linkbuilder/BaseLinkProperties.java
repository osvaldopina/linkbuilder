package com.github.osvaldopina.linkbuilder;

import com.github.osvaldopina.linkbuilder.fromcall.MethodCall;
import com.github.osvaldopina.linkbuilder.template.conditionalsubustitution.ConditionalVariableSubstituionStrategies;
import com.github.osvaldopina.linkbuilder.template.conditionalsubustitution.ConditionalVariableSubstitutionStrategy;

import java.util.List;

public class BaseLinkProperties implements LinkProperties {

    private String rel;

    private String whenExpression;

    private Object payload;

    private MethodCall methodCall;

    private List<ConditionalVariableSubstitutionStrategy> conditionalVariableSubstituionStrategyList;

    private boolean templated;

    public BaseLinkProperties() {

    }

    public BaseLinkProperties(BaseLinkProperties linkProperties) {
        this.rel = linkProperties.getRel();
        this.whenExpression = linkProperties.getWhenExpression();
        this.payload = linkProperties.getPayload();
        this.methodCall = linkProperties.getMethodCall();
        this.conditionalVariableSubstituionStrategyList = linkProperties.conditionalVariableSubstituionStrategyList;
        this.templated = linkProperties.isTemplated();
    }

    @Override
    public String getRel() {
        return rel;
    }

    @Override
    public String getWhenExpression() {
        return whenExpression;
    }

    @Override
    public Object getPayload() {
        return payload;
    }

    @Override
    public MethodCall getMethodCall() {
        return methodCall;
    }

    @Override
    public ConditionalVariableSubstituionStrategies getConditionalVariableSubstituionStrategies() {
         return new ConditionalVariableSubstituionStrategies(conditionalVariableSubstituionStrategyList);
    }

    @Override
    public void addConditionalVariableSubstitutionStrategy(
            ConditionalVariableSubstitutionStrategy conditionalVariableSubstitutionStrategy) {

        conditionalVariableSubstituionStrategyList.add(conditionalVariableSubstitutionStrategy);

    }

    @Override
    public void setRel(String rel) {
        this.rel = rel;
    }

    @Override
    public void setWhenExpression(String whenExpression) {
        this.whenExpression = whenExpression;
    }

    @Override
    public void setPayload(Object payload) {
        this.payload = payload;
    }

    @Override
    public void setMethodCall(MethodCall methodCall) {
        this.methodCall = methodCall;
    }

    @Override
    public boolean isTemplated() {
        return templated;
    }

    @Override
    public void setTemplated(boolean templated) {
        this.templated = templated;
    }
}
