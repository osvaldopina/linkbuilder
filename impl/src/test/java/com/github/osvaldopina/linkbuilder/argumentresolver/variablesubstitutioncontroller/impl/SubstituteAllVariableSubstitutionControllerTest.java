package com.github.osvaldopina.linkbuilder.argumentresolver.variablesubstitutioncontroller.impl;

import org.easymock.EasyMockRule;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.core.MethodParameter;

import static org.junit.Assert.*;

public class SubstituteAllVariableSubstitutionControllerTest  extends EasyMockSupport {

    @Rule
    public EasyMockRule mocks = new EasyMockRule(this);

    @Mock
    private MethodParameter methodParameter;

    private String variableName;

    private Object parameterValue;

    @TestSubject
    private SubstituteAllVariableSubstitutionController substituteAllVariableSubstitutionController =
            new SubstituteAllVariableSubstitutionController();


    @Test
    public void notSubstituteParameter() throws Exception {

        replayAll();
        assertTrue(substituteAllVariableSubstitutionController.substitute(methodParameter, "var",  null));
        verifyAll();
    }



}