package com.github.osvaldopina.linkbuilder.methodtemplate.impl;

import com.github.osvaldopina.linkbuilder.annotation.GenerateUriTemplateFor;
import com.github.osvaldopina.linkbuilder.utils.TemplateBuilderInstrospectionUtils;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Method;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TemplateGeneratorImplIntrospectorTest {

    TemplateBuilderInstrospectionUtils templateBuilderInstrospectionUtils;

    private Method nonAnnotatedMethodNonAnnotatedClass;
    private Method nonAnnotatedMethodAnnotatedClass;
    private Method annotatedMethodNonAnnotatedClass;
    private Method annotatedMethodAnnotatedClass;

    @Before
    public void setUp() throws Exception {
        templateBuilderInstrospectionUtils = new TemplateBuilderInstrospectionUtils();

        nonAnnotatedMethodNonAnnotatedClass = NonAnnotatedClass.class.getMethod("nonAnnotatedMethod");
        nonAnnotatedMethodAnnotatedClass = AnnotatedClass.class.getDeclaredMethod("nonAnnotatedMethod");
        annotatedMethodNonAnnotatedClass = NonAnnotatedClass.class.getDeclaredMethod("annotatedMethod");
        annotatedMethodAnnotatedClass = AnnotatedClass.class.getDeclaredMethod("annotatedMethod");

    }

    @Test
    public void haveToGenerateTemplateForNonAnnotatedClassNonAnnotatedMethod() throws Exception {
        assertFalse(templateBuilderInstrospectionUtils.haveToGenerateTemplateFor(nonAnnotatedMethodNonAnnotatedClass));
    }

    @Test
    public void haveToGenerateTemplateForAnnotatedClassNonAnnotatedMethod() throws Exception {
        assertTrue(templateBuilderInstrospectionUtils.haveToGenerateTemplateFor(annotatedMethodNonAnnotatedClass));
    }

    @Test
    public void haveToGenerateTemplateForNonAnnotatedClassAnnotatedMethod() throws Exception {
        assertTrue(templateBuilderInstrospectionUtils.haveToGenerateTemplateFor(nonAnnotatedMethodAnnotatedClass));
    }

    @Test
    public void haveToGenerateTemplateForAnnotatedClassAnnotatedMethod() throws Exception {
        assertTrue(templateBuilderInstrospectionUtils.haveToGenerateTemplateFor(annotatedMethodAnnotatedClass));
    }

    public static class NonAnnotatedClass {

        public void nonAnnotatedMethod() {}

        @GenerateUriTemplateFor
        public void annotatedMethod() {}
    }

    @GenerateUriTemplateFor
    public static class AnnotatedClass {

        public void nonAnnotatedMethod() {}

        @GenerateUriTemplateFor
        public void annotatedMethod() {}
    }

}