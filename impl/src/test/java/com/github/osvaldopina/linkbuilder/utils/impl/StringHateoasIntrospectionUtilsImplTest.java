package com.github.osvaldopina.linkbuilder.utils.impl;

import com.github.osvaldopina.linkbuilder.annotation.GenerateUriTemplateFor;
import org.junit.Before;
import org.junit.Test;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Method;

import static org.junit.Assert.*;

public class StringHateoasIntrospectionUtilsImplTest {

    private Method method;

    private StringHateoasIntrospectionUtilsImpl annotationUtilsImpl;

    private Method nonAnnotatedMethodGenerateUriTemplateFor;
    private Method annotatedMethodGenerateUriTemplateFor;


    @Before
    public void setUp() throws Exception {
        method = StringHateoasIntrospectionUtilsImplTest.class.getMethod("method", String.class, String.class, String.class);

        annotatedMethodGenerateUriTemplateFor = GenerateUriTemplateForClass.class.getMethod("annotatedMethod");
        nonAnnotatedMethodGenerateUriTemplateFor = GenerateUriTemplateForClass.class.getDeclaredMethod("nonAnnotatedMethod");



        annotationUtilsImpl = new StringHateoasIntrospectionUtilsImpl();


    }

    @Test
    public void isPathVariableParameterTrue() throws Exception {

        assertTrue(annotationUtilsImpl.isPathVariableParameter(method, 1));

    }

    @Test
    public void isPathVariableParameterFalse() throws Exception {

        assertFalse(annotationUtilsImpl.isPathVariableParameter(method, 0));

    }

    @Test
    public void getPathVariableName() throws Exception {

        assertEquals("path-param", annotationUtilsImpl.getPathVariableName(method, 1));

    }

    @Test
    public void isQueryVariableParameterTrue() throws Exception {

        assertTrue(annotationUtilsImpl.isQueryVariableParameter(method, 0));

    }

    @Test
    public void isQueryVariableParameterFalse() throws Exception {

        assertFalse(annotationUtilsImpl.isQueryVariableParameter(method, 1));

    }

    @Test
    public void getQueryVariableName() throws Exception {

        assertEquals("query-param", annotationUtilsImpl.getQueryVariableName(method, 0));

    }

    @Test
    public void isRequestBodyVariableParameterTrue() throws Exception {

        assertTrue(annotationUtilsImpl.isRequestBodyVariableParameter(method, 2));

    }

    @Test
    public void isRequestBodyVariableParameterFalse() throws Exception {

        assertFalse(annotationUtilsImpl.isRequestBodyVariableParameter(method, 1));

    }

    @Test
    public void haveToGenerateTemplateForNonAnnotatedMethod() throws Exception {
        assertFalse(annotationUtilsImpl.haveToGenerateTemplateFor(nonAnnotatedMethodGenerateUriTemplateFor));
    }

    @Test
    public void haveToGenerateTemplateForAnnotatedMethod() throws Exception {
        assertTrue(annotationUtilsImpl.haveToGenerateTemplateFor(annotatedMethodGenerateUriTemplateFor));
    }


    public void method(
            @RequestParam("query-param") String requestParam,
            @PathVariable("path-param") String pathParam,
            @RequestBody String requestBody) {
    }

    public static class GenerateUriTemplateForClass {

        public void nonAnnotatedMethod() {
        }

        @GenerateUriTemplateFor
        public void annotatedMethod() {
        }
    }

    @RestController
    public static class Controller {

        public void controllerMethod() {

        }
    }



}