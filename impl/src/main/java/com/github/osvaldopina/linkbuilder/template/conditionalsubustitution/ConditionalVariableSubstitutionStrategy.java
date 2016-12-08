package com.github.osvaldopina.linkbuilder.template.conditionalsubustitution;

import com.github.osvaldopina.linkbuilder.template.Variable;

public interface ConditionalVariableSubstitutionStrategy {

    boolean shouldSubstitute(Variable variable, Object value);

}
