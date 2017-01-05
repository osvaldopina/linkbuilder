package com.github.osvaldopina.linkbuilder.utils.impl;

import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;

public class UriTemplateQueryVarNameValidatorTest {

    @Test
    public void isValidValidQueryVarName() throws Exception {
//        assertTrue(UriTemplateQueryVarNameValidator.isValid("var1"));
    }

    @Test
    public void isValidNonValidQueryVarName() throws Exception {
//        assertFalse(UriTemplateQueryVarNameValidator.isValid("var-1"));
    }

    @Test
    public void assertValidValidValidQueryVarName() throws Exception {
//        UriTemplateQueryVarNameValidator.assertValid("var1");
    }

    @Test(expected = IllegalArgumentException.class)
    @Ignore
    public void assertValidNonValidQueryVarName() throws Exception {
//        UriTemplateQueryVarNameValidator.assertValid("var-1");
    }
}