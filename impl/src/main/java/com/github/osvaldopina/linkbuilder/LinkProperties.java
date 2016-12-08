package com.github.osvaldopina.linkbuilder;

import com.github.osvaldopina.linkbuilder.fromcall.MethodCall;
import com.github.osvaldopina.linkbuilder.template.conditionalsubustitution.ConditionalVariableSubstituionStrategies;
import com.github.osvaldopina.linkbuilder.template.conditionalsubustitution.ConditionalVariableSubstitutionStrategy;

public interface LinkProperties {

    String getRel();

    String getWhenExpression();

    Object getPayload();

    MethodCall getMethodCall();

    ConditionalVariableSubstituionStrategies getConditionalVariableSubstituionStrategies();

    void addConditionalVariableSubstitutionStrategy(
            ConditionalVariableSubstitutionStrategy conditionalVariableSubstitutionStrategy);

    void setRel(String rel);

    void setWhenExpression(String whenExpression);

    void setPayload(Object payload);

    void setMethodCall(MethodCall methodCall);

    boolean isTemplated();

    void setTemplated(boolean templated);
}
