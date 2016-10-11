package com.github.osvaldopina.linkbuilder.argumentresolver.variablesubstitutioncontroller.impl;

import org.easymock.*;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.core.MethodParameter;

import java.lang.reflect.Method;

import static org.junit.Assert.*;

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