package com.github.osvaldopina.linkbuilder.template;

import com.github.osvaldopina.linkbuilder.LinkBuilderException;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

public class VariablesTest {

    Variable variable;
    Variables variables;

    @Before
    public void setUp() throws Exception {

        variable = new Variable("var1",VariableType.QUERY, VariablesTest.class.getMethod("toString"), 1);
        variables = new Variables(Arrays.asList(variable));

    }

    @Test
    public void getVariableExists() throws Exception {

        assertThat(variables.get("var1"), sameInstance(variable));
    }

    @Test
    public void getVariableDoesExists() throws Exception {
        try {
            variables.get("var2");
            fail("Must throw exception");
        } catch (LinkBuilderException e) {
            assertThat(e.getMessage(), is("Could not find variable with name [var2]. The variables are:[var1]"));
        }

    }

    @Test
    public void getVariableNames() throws Exception {

        assertThat(variables.getVariableNames(),hasSize(1));
        assertThat(variables.getVariableNames(),hasItem("var1"));

    }

}