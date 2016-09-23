package com.github.osvaldopina.linkbuilder.argumentresolver.variablesubstitutioncontroller.impl;

import org.easymock.EasyMockRule;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.core.MethodParameter;

import static org.junit.Assert.*;

public class NotSubstituteVariableNameVariableSubstitutionControllerTest   extends EasyMockSupport {

    @Rule
    public EasyMockRule mocks = new EasyMockRule(this);

    @Mock
    private MethodParameter methodParameter;

    private String variableName;

    private Object parameterValue;

    @TestSubject
    private NotSubstituteVariableNameVariableSubstitutionController
            notSubstituteVariableNameVariableSubstitutionController =
            new NotSubstituteVariableNameVariableSubstitutionController("var");


    @Test
    public void notSubstituteParameter() throws Exception {

        replayAll();
        assertFalse(notSubstituteVariableNameVariableSubstitutionController.substitute(methodParameter, "var",  null));
        verifyAll();
    }

    @Test
    public void substituteParameter() throws Exception {

        replayAll();
        assertTrue(notSubstituteVariableNameVariableSubstitutionController.substitute(methodParameter, "other-var",  null));
        verifyAll();
    }

}