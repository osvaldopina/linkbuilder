package com.github.osvaldopina.linkbuilder.argumentresolver.variablesubstitutioncontroller.impl;

import org.easymock.EasyMockRule;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.core.MethodParameter;

import java.lang.reflect.Method;

import static org.junit.Assert.*;

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