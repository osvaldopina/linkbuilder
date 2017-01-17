package com.github.osvaldopina.linkbuilder.urigeneration.link.methodcall.impl;

import com.github.osvaldopina.linkbuilder.template.conditionalsubustitution.ConditionalVariableSubstitutionStrategies;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class DefaultConditionalVariableSubstitutionStategiesFactoryTest {


    private DefaultConditionalVariableSubstitutionStategiesFactory defaultConditionalVariableSubstitutionStategiesFactory;

    @Before
    public void setUp() {
        defaultConditionalVariableSubstitutionStategiesFactory =
                new DefaultConditionalVariableSubstitutionStategiesFactory();
    }

    @Test
    public void create() {

        ConditionalVariableSubstitutionStrategies conditionalVariableSubstitutionStrategies =
                defaultConditionalVariableSubstitutionStategiesFactory.create();


        assertThat(conditionalVariableSubstitutionStrategies,
                isA(ConditionalVariableSubstitutionStrategies.class));
    }

}