package com.github.osvaldopina.linkbuilder.template.impl;

import com.damnhandy.uri.template.UriTemplate;
import com.github.osvaldopina.linkbuilder.template.*;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class UriTemplateVariableSetterTest {

    private UriTemplateVariableSetter uriTemplateVariableSetter;

    private UriTemplate uriTemplate;

    private VariableValues variableValues;

    private VariableValue variableValue;

    private Variable variable;

    private Variables variables;


    @Before
    public void setUp() throws Exception {
        uriTemplateVariableSetter = new UriTemplateVariableSetter();

        variable = new Variable("var1", VariableType.QUERY, Object.class.getMethod("toString"), 0);

        variables = new Variables(Arrays.asList(variable));

        variableValue = new VariableValue(variable, "value");

        variableValues = new VariableValues(Arrays.asList(variableValue));

        uriTemplate = UriTemplate.fromTemplate("{var1}");
    }


    @Test
    public void createNewTemplateAndSetVariables() throws Exception {
        UriTemplate template = uriTemplateVariableSetter.createNewTemplateAndSetVariables(
                uriTemplate,
                variables,
                variableValues);

        assertThat(template.expand(), is("value"));

    }

}