package com.github.osvaldopina.linkbuilder.template.conditionalsubustitution.core;

import com.github.osvaldopina.linkbuilder.template.Variable;
import com.github.osvaldopina.linkbuilder.template.conditionalsubustitution.ConditionalVariableSubstitutionStrategy;

public class DontSubstituteNullValue implements ConditionalVariableSubstitutionStrategy {


    @Override
    public boolean shouldSubstitute(Variable variable, Object value) {
        return value != null;
    }
}
