package com.github.osvaldopina.linkbuilder.argumentresolver.variablesubstitutioncontroller.impl;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class SubstituteAllVariableSubstitutionControllerTest  {

    private SubstituteAllVariableSubstitutionController substituteAllVariableSubstitutionController;

    @Before
    public void setup() {
         substituteAllVariableSubstitutionController =
                new SubstituteAllVariableSubstitutionController();

    }

    @Test
    public void notSubstituteParameter() throws Exception {

        assertTrue(substituteAllVariableSubstitutionController.substitute(null, 0, null,  null));
    }



}