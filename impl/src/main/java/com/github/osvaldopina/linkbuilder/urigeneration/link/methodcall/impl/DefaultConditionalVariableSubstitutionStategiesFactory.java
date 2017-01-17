package com.github.osvaldopina.linkbuilder.urigeneration.link.methodcall.impl;

import com.github.osvaldopina.linkbuilder.template.conditionalsubustitution.ConditionalVariableSubstitutionStrategies;

public class DefaultConditionalVariableSubstitutionStategiesFactory {


    public static final DefaultConditionalVariableSubstitutionStategiesFactory INSTANCE =
            new DefaultConditionalVariableSubstitutionStategiesFactory();

    DefaultConditionalVariableSubstitutionStategiesFactory() {

    }

    public ConditionalVariableSubstitutionStrategies create() {
        return new ConditionalVariableSubstitutionStrategies();
    }
}
