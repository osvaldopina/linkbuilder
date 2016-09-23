package com.github.osvaldopina.linkbuilder.argumentresolver.variablesubstitutioncontroller.impl;

import org.easymock.EasyMockRule;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.core.MethodParameter;

import static org.junit.Assert.*;

public class SubstituteNoneVariableSubstitutionControllerTest  extends EasyMockSupport {

    @Rule
    public EasyMockRule mocks = new EasyMockRule(this);

    @Mock
    private MethodParameter methodParameter;

    private String variableName;

    private Object parameterValue;

    @TestSubject
    private SubstituteNoneVariableSubstitutionController substituteNoneVariableSubstitutionController =
            new SubstituteNoneVariableSubstitutionController();


    @Test
    public void substituteNone() throws Exception {

        replayAll();
        assertFalse(substituteNoneVariableSubstitutionController.substitute(methodParameter, "var",  null));
        verifyAll();


    }
}