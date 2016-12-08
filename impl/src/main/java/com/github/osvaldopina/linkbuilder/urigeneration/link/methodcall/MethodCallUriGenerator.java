package com.github.osvaldopina.linkbuilder.urigeneration.link.methodcall;

import com.github.osvaldopina.linkbuilder.fromcall.MethodCall;
import com.github.osvaldopina.linkbuilder.template.conditionalsubustitution.ConditionalVariableSubstituionStrategies;

public interface MethodCallUriGenerator {

    String generateUri(MethodCall methodCall, Object payload);

    String generateUri(MethodCall methodCall, Object payload,
                       ConditionalVariableSubstituionStrategies conditionalVariableSubstituionStrategies);

}
