package com.github.osvaldopina.linkbuilder.argumentresolver.variablesubstitutioncontroller.impl;

import org.easymock.TestSubject;
import org.junit.Test;

import java.lang.reflect.Method;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class NotSubstituteNullValueVariableSubstitutionControllerTest {

    private Method method = null;

    private String variableName;

    private Object parameterValue = new Object();

    @TestSubject
    private NotSubstituteNullValueVariableSubstitutionController notSubstituteNullValueVariableSubstitutionController =
            new NotSubstituteNullValueVariableSubstitutionController();

    @Test
    public void notSubstituteNullValue() throws Exception {

        assertFalse(notSubstituteNullValueVariableSubstitutionController.substitute(method, 0, variableName, null));

    }

    @Test
    public void substituteNonNullValue() throws Exception {

        assertTrue(notSubstituteNullValueVariableSubstitutionController.substitute(method, 0, variableName, parameterValue));

    }

}