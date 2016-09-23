package com.github.osvaldopina.linkbuilder.argumentresolver.variablesubstitutioncontroller.impl;

import org.easymock.EasyMockRule;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.core.MethodParameter;

import static org.junit.Assert.*;

public class NotSubstituteNullValueVariableSubstitutionControllerTest extends EasyMockSupport {

    @Rule
    public EasyMockRule mocks = new EasyMockRule(this);

    @Mock
    private MethodParameter methodParameter;

    private String variableName;

    private Object parameterValue;

    @TestSubject
    private NotSubstituteNullValueVariableSubstitutionController notSubstituteNullValueVariableSubstitutionController =
            new NotSubstituteNullValueVariableSubstitutionController();

    @Test
    public void notSubstituteNullValue() throws Exception {

        replayAll();
        assertFalse(notSubstituteNullValueVariableSubstitutionController.substitute(methodParameter, variableName, parameterValue));
        verifyAll();

    }

    @Test
    public void substituteNonNullValue() throws Exception {

        parameterValue = new Object();

        replayAll();
        assertTrue(notSubstituteNullValueVariableSubstitutionController.substitute(methodParameter, variableName, parameterValue));
        verifyAll();

    }

}