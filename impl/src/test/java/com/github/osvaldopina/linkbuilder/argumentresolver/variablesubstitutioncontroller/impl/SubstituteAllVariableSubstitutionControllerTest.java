package com.github.osvaldopina.linkbuilder.argumentresolver.variablesubstitutioncontroller.impl;

import org.easymock.EasyMockRule;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.core.MethodParameter;

import static org.junit.Assert.*;

public class SubstituteAllVariableSubstitutionControllerTest  {

    private SubstituteAllVariableSubstitutionController substituteAllVariableSubstitutionController;

    @Before
    public void setup() {
         substituteAllVariableSubstitutionController =
                new SubstituteAllVariableSubstitutionController();

    }

    @Test
    public void notSubstituteParameter() throws Exception {

        assertTrue(substituteAllVariableSubstitutionController.substitute(null, 0, null,  null));
    }



}