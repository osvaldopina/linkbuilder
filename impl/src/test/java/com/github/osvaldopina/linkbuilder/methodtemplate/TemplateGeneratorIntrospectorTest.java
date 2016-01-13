package com.github.osvaldopina.linkbuilder.methodtemplate;

import com.github.osvaldopina.linkbuilder.annotation.GenerateUriTemplateFor;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Method;

import static org.junit.Assert.*;

/**
 * Created by osvaldopina on 1/4/16.
 */
public class TemplateGeneratorIntrospectorTest {

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