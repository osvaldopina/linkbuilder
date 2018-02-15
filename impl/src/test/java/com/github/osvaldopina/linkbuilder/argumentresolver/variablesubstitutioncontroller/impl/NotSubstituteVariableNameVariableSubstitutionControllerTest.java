package com.github.osvaldopina.linkbuilder.argumentresolver.variablesubstitutioncontroller.impl;

import org.easymock.EasyMockRule;
import org.easymock.EasyMockSupport;
import org.easymock.TestSubject;
import org.junit.Rule;
import org.junit.Test;

import java.lang.reflect.Method;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class NotSubstituteVariableNameVariableSubstitutionControllerTest   extends EasyMockSupport {

    @Rule
    public EasyMockRule mocks = new EasyMockRule(this);

    private Method method;

    private int parameterIndex =0;

    private String variableName;

    private Object parameterValue;

    @TestSubject
    private NotSubstituteVariableNameVariableSubstitutionController
            notSubstituteVariableNameVariableSubstitutionController =
            new NotSubstituteVariableNameVariableSubstitutionController("var");


    @Test
    public void notSubstituteParameter() throws Exception {

        replayAll();
        assertFalse(notSubstituteVariableNameVariableSubstitutionController.substitute(
                method,
                parameterIndex,
                "var",
                null)
        );
        verifyAll();
    }

    @Test
    public void substituteParameter() throws Exception {

        replayAll();
        assertTrue(notSubstituteVariableNameVariableSubstitutionController.substitute(
                method,
                parameterIndex,
                "other-var",
                null)
        );
        verifyAll();
    }

}