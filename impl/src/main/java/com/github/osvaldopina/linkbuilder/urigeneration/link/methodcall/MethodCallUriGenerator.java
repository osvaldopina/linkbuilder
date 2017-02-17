package com.github.osvaldopina.linkbuilder.urigeneration.link.methodcall;

import com.github.osvaldopina.linkbuilder.fromcall.MethodCall;
import com.github.osvaldopina.linkbuilder.template.conditionalsubustitution.ConditionalVariableSubstitutionStrategies;

public interface MethodCallUriGenerator {

    String generateUri(MethodCall methodCall, Object resource, boolean templated);

    String generateUri(MethodCall methodCall, Object resource,
                       ConditionalVariableSubstitutionStrategies conditionalVariableSubstitutionStrategies,
                       boolean templated);

}
