package com.github.osvaldopina.linkbuilder.argumentresolver.variablesubstitutioncontroller.impl;

import com.github.osvaldopina.linkbuilder.argumentresolver.variablesubstitutioncontroller.VariableSubstitutionController;
import org.easymock.*;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.core.MethodParameter;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class VariableSubstitutionControllersTest extends EasyMockSupport {

    @Rule
    public EasyMockRule mocks = new EasyMockRule(this);

    @Mock
    private MethodParameter methodParameter;

    private String variableName = "var";

    @Mock
    private Object parameterValue;

    @Mock
    private VariableSubstitutionController controller1;

    @Mock
    private VariableSubstitutionController controller2;

    @TestSubject
    private VariableSubstitutionControllers variableSubstitutionControllers =
            new VariableSubstitutionControllers();


    @Test
    public void substituteAllControllersTrue() throws Exception {

        EasyMock.expect(controller1.substitute(methodParameter, variableName, parameterValue)).andReturn(true);
        EasyMock.expect(controller2.substitute(methodParameter, variableName, parameterValue)).andReturn(true);

        variableSubstitutionControllers.addVariableSubstitutionControllerAggregator(controller1);
        variableSubstitutionControllers.addVariableSubstitutionControllerAggregator(controller2);


        replayAll();
        assertTrue(variableSubstitutionControllers.substitute(methodParameter, variableName, parameterValue));
        verifyAll();

    }

    @Test
    public void substituteFirstControllerFalse() throws Exception {

        EasyMock.expect(controller1.substitute(methodParameter, variableName, parameterValue)).andReturn(false);

        variableSubstitutionControllers.addVariableSubstitutionControllerAggregator(controller1);
        variableSubstitutionControllers.addVariableSubstitutionControllerAggregator(controller2);


        replayAll();
        assertFalse(variableSubstitutionControllers.substitute(methodParameter, variableName, parameterValue));
        verifyAll();

    }

    @Test
    public void substituteSecondControllersFalse() throws Exception {

        EasyMock.expect(controller1.substitute(methodParameter, variableName, parameterValue)).andReturn(true);
        EasyMock.expect(controller2.substitute(methodParameter, variableName, parameterValue)).andReturn(false);

        variableSubstitutionControllers.addVariableSubstitutionControllerAggregator(controller1);
        variableSubstitutionControllers.addVariableSubstitutionControllerAggregator(controller2);


        replayAll();
        assertFalse(variableSubstitutionControllers.substitute(methodParameter, variableName, parameterValue));
        verifyAll();

    }


}