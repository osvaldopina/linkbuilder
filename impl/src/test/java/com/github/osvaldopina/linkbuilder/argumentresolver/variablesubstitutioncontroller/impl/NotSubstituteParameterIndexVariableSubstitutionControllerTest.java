package com.github.osvaldopina.linkbuilder.argumentresolver.variablesubstitutioncontroller.impl;

import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Method;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class NotSubstituteParameterIndexVariableSubstitutionControllerTest  {


    private Method method;

    private String variableName;

    private Object parameterValue;

    private NotSubstituteParameterIndexVariableSubstitutionController notSubstituteParameterIndexVariableSubstitutionController;

    @Before
    public void setUp() {
        notSubstituteParameterIndexVariableSubstitutionController = new NotSubstituteParameterIndexVariableSubstitutionController(1);
    }

    @Test
    public void dontSubstituteParameter() throws Exception {

        assertFalse(notSubstituteParameterIndexVariableSubstitutionController.substitute(null,1, "name", null));

    }

    @Test
    public void substituteParameter() throws Exception {

        assertTrue(notSubstituteParameterIndexVariableSubstitutionController.substitute(null,0, "name", null));

    }

}