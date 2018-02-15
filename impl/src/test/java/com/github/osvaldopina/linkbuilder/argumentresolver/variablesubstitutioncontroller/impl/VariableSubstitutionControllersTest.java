package com.github.osvaldopina.linkbuilder.argumentresolver.variablesubstitutioncontroller.impl;

import com.github.osvaldopina.linkbuilder.argumentresolver.variablesubstitutioncontroller.VariableSubstitutionController;
import org.easymock.EasyMock;
import org.easymock.EasyMockRule;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class VariableSubstitutionControllersTest extends EasyMockSupport {

    @Rule
    public EasyMockRule mocks = new EasyMockRule(this);

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

        EasyMock.expect(controller1.substitute(null, 0, variableName, parameterValue)).andReturn(true);
        EasyMock.expect(controller2.substitute(null, 0 , variableName, parameterValue)).andReturn(true);

        variableSubstitutionControllers.addVariableSubstitutionControllerAggregator(controller1);
        variableSubstitutionControllers.addVariableSubstitutionControllerAggregator(controller2);


        replayAll();
        assertTrue(variableSubstitutionControllers.substitute(null, 0, variableName, parameterValue));
        verifyAll();

    }

    @Test
    public void substituteFirstControllerFalse() throws Exception {

        EasyMock.expect(controller1.substitute(null, 0, variableName, parameterValue)).andReturn(false);

        variableSubstitutionControllers.addVariableSubstitutionControllerAggregator(controller1);
        variableSubstitutionControllers.addVariableSubstitutionControllerAggregator(controller2);


        replayAll();
        assertFalse(variableSubstitutionControllers.substitute(null, 0, variableName, parameterValue));
        verifyAll();

    }

    @Test
    public void substituteSecondControllersFalse() throws Exception {

        EasyMock.expect(controller1.substitute(null, 0, variableName, parameterValue)).andReturn(true);
        EasyMock.expect(controller2.substitute(null, 0, variableName, parameterValue)).andReturn(false);

        variableSubstitutionControllers.addVariableSubstitutionControllerAggregator(controller1);
        variableSubstitutionControllers.addVariableSubstitutionControllerAggregator(controller2);


        replayAll();
        assertFalse(variableSubstitutionControllers.substitute(null, 0, variableName, parameterValue));
        verifyAll();

    }


}