package com.github.osvaldopina.linkbuilder.template.conditionalsubustitution.core;

import com.github.osvaldopina.linkbuilder.template.Variable;
import com.github.osvaldopina.linkbuilder.template.conditionalsubustitution.ConditionalVariableSubstitutionStrategy;

public class DontSubstituteParameterIndex implements ConditionalVariableSubstitutionStrategy {

    private int parameterIndex;

    public DontSubstituteParameterIndex(int parameterIndex) {
        this.parameterIndex = parameterIndex;
    }

    @Override
    public boolean shouldSubstitute(Variable variable, Object value) {
        return variable.getParameterIndex() != parameterIndex;
    }
}
