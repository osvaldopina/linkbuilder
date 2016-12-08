package com.github.osvaldopina.linkbuilder.template.conditionalsubustitution.core;

import com.github.osvaldopina.linkbuilder.template.Variable;
import com.github.osvaldopina.linkbuilder.template.conditionalsubustitution.ConditionalVariableSubstitutionStrategy;

public class DontSubstituteVariableName implements ConditionalVariableSubstitutionStrategy {

    private String variableName;

    public DontSubstituteVariableName(String variableName) {
        this.variableName = variableName;
    }


    @Override
    public boolean shouldSubstitute(Variable variable, Object value) {
        return variable.getName().equals(variableName);
    }
}
