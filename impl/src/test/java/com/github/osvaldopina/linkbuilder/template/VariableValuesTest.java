package com.github.osvaldopina.linkbuilder.template;

import com.github.osvaldopina.linkbuilder.LinkBuilderException;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

public class VariableValuesTest {

    Variable variable;
    VariableValue variableValue;
    VariableValues variableValues;

    @Before
    public void setUp() throws Exception {

        variable = new Variable("var1", VariableType.QUERY, VariableValuesTest.class.getMethod("toString"), 1);
        variableValue = new VariableValue(variable, new Object());
        variableValues = new VariableValues(Arrays.asList(variableValue));

    }

    @Test
    public void getVariableValueExists() throws Exception {

        assertThat(variableValues.get("var1"), sameInstance(variableValue));
    }

    @Test
    public void getVariableValueDoesExists() throws Exception {
        try {
            variableValues.get("var2");
            fail("Must throw exception");
        } catch (LinkBuilderException e) {
            assertThat(e.getMessage(),
                    is("Could not find variable value for variable name [var2]. The variable value names are:[var1]")
            );
        }

    }

    @Test
    public void getVariableValueNames() throws Exception {

        assertThat(variableValues.getVariableNames(),hasSize(1));
        assertThat(variableValues.getVariableNames(),hasItem("var1"));

    }

    @Test
    public void getVariableValues() throws Exception {

        assertThat(variableValues.getVariableValueList(),hasSize(1));
        assertThat(variableValues.getVariableValueList(), hasItem(variableValue));

    }


}