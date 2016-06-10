package com.github.osvaldopina.linkbuilder.methodtemplate.impl;

import com.github.osvaldopina.linkbuilder.annotation.GenerateUriTemplateFor;
import com.github.osvaldopina.linkbuilder.methodtemplate.impl.TemplateGeneratorIntrospector;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Method;

import static org.junit.Assert.*;

public class TemplateGeneratorImplIntrospectorTest {

    TemplateGeneratorIntrospector templateGeneratorIntrospector;

    private Method nonAnnotatedMethodNonAnnotatedClass;
    private Method nonAnnotatedMethodAnnotatedClass;
    private Method annotatedMethodNonAnnotatedClass;
    private Method annotatedMethodAnnotatedClass;

    @Before
    public void setUp() throws Exception {
        templateGeneratorIntrospector = new TemplateGeneratorIntrospector();

        nonAnnotatedMethodNonAnnotatedClass = NonAnnotatedClass.class.getMethod("nonAnnotatedMethod");
        nonAnnotatedMethodAnnotatedClass = AnnotatedClass.class.getDeclaredMethod("nonAnnotatedMethod");
        annotatedMethodNonAnnotatedClass = NonAnnotatedClass.class.getDeclaredMethod("annotatedMethod");
        annotatedMethodAnnotatedClass = AnnotatedClass.class.getDeclaredMethod("annotatedMethod");

    }

    @Test
    public void haveToGenerateTemplateForNonAnnotatedClassNonAnnotatedMethod() throws Exception {
        assertFalse(templateGeneratorIntrospector.haveToGenerateTemplateFor(nonAnnotatedMethodNonAnnotatedClass));
    }

    @Test
    public void haveToGenerateTemplateForAnnotatedClassNonAnnotatedMethod() throws Exception {
        assertTrue(templateGeneratorIntrospector.haveToGenerateTemplateFor(annotatedMethodNonAnnotatedClass));
    }

    @Test
    public void haveToGenerateTemplateForNonAnnotatedClassAnnotatedMethod() throws Exception {
        assertTrue(templateGeneratorIntrospector.haveToGenerateTemplateFor(nonAnnotatedMethodAnnotatedClass));
    }

    @Test
    public void haveToGenerateTemplateForAnnotatedClassAnnotatedMethod() throws Exception {
        assertTrue(templateGeneratorIntrospector.haveToGenerateTemplateFor(annotatedMethodAnnotatedClass));
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