package com.github.osvaldopina.linkbuilder.template.conditionalsubustitution;

import com.github.osvaldopina.linkbuilder.template.Variable;
import com.github.osvaldopina.linkbuilder.template.conditionalsubustitution.core.SubstituteAll;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ConditionalVariableSubstituionStrategies implements ConditionalVariableSubstitutionStrategy{

    private List<ConditionalVariableSubstitutionStrategy> conditionalVariableSubstitutionStrategies =
            new ArrayList<ConditionalVariableSubstitutionStrategy>(
                    Arrays.asList(new SubstituteAll())
            );


    public ConditionalVariableSubstituionStrategies() {
    }

    public ConditionalVariableSubstituionStrategies(
            List<ConditionalVariableSubstitutionStrategy> conditionalVariableSubstitutionStrategies) {
        this.conditionalVariableSubstitutionStrategies.addAll(conditionalVariableSubstitutionStrategies);
    }


    @Override
    public boolean shouldSubstitute(Variable variable, Object value) {
        for(ConditionalVariableSubstitutionStrategy strategy: conditionalVariableSubstitutionStrategies) {
            if (! strategy.shouldSubstitute(variable, value)) {
                return false;
            }
        }
        return true;
    }
}
