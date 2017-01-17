package com.github.osvaldopina.linkbuilder.utils;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;

public class UriTemplateQueryVarNameValidatorTest {

    private UriTemplateQueryVarNameValidator uriTemplateQueryVarNameValidator;

    @Before
    public void setUp() {
        uriTemplateQueryVarNameValidator = new UriTemplateQueryVarNameValidator();
    }

    @Test
    public void isValidValidQueryVarName() throws Exception {
        assertTrue(uriTemplateQueryVarNameValidator.isValid("var1"));
    }

    @Test
    public void isValidNonValidQueryVarName() throws Exception {
        assertFalse(uriTemplateQueryVarNameValidator.isValid("var-1"));
    }

    @Test
    public void assertValidValidValidQueryVarName() throws Exception {
        uriTemplateQueryVarNameValidator.assertValid("var1");
    }

    @Test(expected = IllegalArgumentException.class)
    public void assertValidNonValidQueryVarName() throws Exception {
        uriTemplateQueryVarNameValidator.assertValid("var-1");
    }
}