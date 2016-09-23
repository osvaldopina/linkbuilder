package com.github.osvaldopina.linkbuilder.argumentresolver.variablesubstitutioncontroller.impl;

import org.easymock.*;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.core.MethodParameter;

import static org.junit.Assert.*;

public class NotSubstituteParameterIndexVariableSubstitutionControllerTest extends EasyMockSupport {

    @Rule
    public EasyMockRule mocks = new EasyMockRule(this);

    @Mock
    private MethodParameter methodParameter;

    private String variableName;

    private Object parameterValue;

    @TestSubject
    private NotSubstituteParameterIndexVariableSubstitutionController notSubstituteParameterIndexVariableSubstitutionController =
            new NotSubstituteParameterIndexVariableSubstitutionController(1);


    @Test
    public void notSubstituteParameter() throws Exception {

        EasyMock.expect(methodParameter.getParameterIndex()).andReturn(1);

        replayAll();
        assertFalse(notSubstituteParameterIndexVariableSubstitutionController.substitute(methodParameter, "name", null));
        verifyAll();

    }

    @Test
    public void substituteParameter() throws Exception {

        EasyMock.expect(methodParameter.getParameterIndex()).andReturn(2);

        replayAll();
        assertTrue(notSubstituteParameterIndexVariableSubstitutionController.substitute(methodParameter, "name", null));
        verifyAll();

    }

}