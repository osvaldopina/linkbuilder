package com.github.osvaldopina.linkbuilder.utils.impl;

import com.github.osvaldopina.linkbuilder.LinkBuilderException;
import com.github.osvaldopina.linkbuilder.annotation.EnableSelfFromCurrentCall;
import com.github.osvaldopina.linkbuilder.annotation.GenerateUriTemplateFor;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.lang.annotation.*;
import java.lang.reflect.Method;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class StringHateoasIntrospectionUtilsImplTest {

    private Method method;

    private StringHateoasIntrospectionUtilsImpl springHateoasIntrospectionUtilsImpl;

    private Method nonAnnotatedMethodGenerateUriTemplateFor;
    private Method annotatedMethodGenerateUriTemplateFor;
    private Method composedAnnotatedMethodGenerateUriTemplateFor;

    private Method nonAnnotatedMethodEnableSelfFromCurrentCall;
    private Method annotatedMethodEnableSelfFromCurrentCall;

    private Method nonAnnotatedMethodComposedAnnotationClass;
    private Method annotatedMethodComposedAnnotationClass;
    private Method composedAnnotatedMethodComposedAnnotationClass;

    private Method nonAnnotatedMethodGenerateUriTemplateForDestinationClass;
    private Method annotatedMethodGenerateUriTemplateForDestinationClass;
    private Method composedAnnotatedMethodGenerateUriTemplateForDestinationClass;




    @Before
    public void setUp() throws Exception {
        method = StringHateoasIntrospectionUtilsImplTest.class.getMethod("method", String.class, String.class, String.class);

        annotatedMethodGenerateUriTemplateFor = GenerateUriTemplateForClass.class.getMethod("annotatedMethod");
        nonAnnotatedMethodGenerateUriTemplateFor = GenerateUriTemplateForClass.class.getDeclaredMethod("nonAnnotatedMethod");
        composedAnnotatedMethodGenerateUriTemplateFor = GenerateUriTemplateForClass.class.getDeclaredMethod("composedAnnotatedMethod");

        annotatedMethodEnableSelfFromCurrentCall = EnableSelfFromCurrentCallClass.class.getMethod("annotatedMethod");
        nonAnnotatedMethodEnableSelfFromCurrentCall = EnableSelfFromCurrentCallClass.class.getMethod("nonAnnotatedMethod");

        nonAnnotatedMethodComposedAnnotationClass = ComposedAnnotationClass.class.getMethod("nonAnnotatedMethod");
        annotatedMethodComposedAnnotationClass = ComposedAnnotationClass.class.getMethod("annotatedMethod");
        composedAnnotatedMethodComposedAnnotationClass = ComposedAnnotationClass.class.getMethod("composedAnnotatedMethod");

        annotatedMethodGenerateUriTemplateFor = GenerateUriTemplateForClass.class.getMethod("annotatedMethod");
        nonAnnotatedMethodGenerateUriTemplateFor = GenerateUriTemplateForClass.class.getDeclaredMethod("nonAnnotatedMethod");
        composedAnnotatedMethodGenerateUriTemplateFor = GenerateUriTemplateForClass.class.getDeclaredMethod("composedAnnotatedMethod");


        nonAnnotatedMethodGenerateUriTemplateForDestinationClass = GenerateUriTemplateForDestinationClass.class.getMethod("nonAnnotatedMethod");
        annotatedMethodGenerateUriTemplateForDestinationClass  = GenerateUriTemplateForDestinationClass.class.getMethod("annotatedMethod");
        composedAnnotatedMethodGenerateUriTemplateForDestinationClass  = GenerateUriTemplateForDestinationClass.class.getMethod("composedAnnotatedMethod");


        springHateoasIntrospectionUtilsImpl = new StringHateoasIntrospectionUtilsImpl();


    }

    @Test
    public void isPathVariableParameterTrue() throws Exception {

        assertTrue(springHateoasIntrospectionUtilsImpl.isPathVariableParameter(method, 1));

    }

    @Test
    public void isPathVariableParameterFalse() throws Exception {

        assertFalse(springHateoasIntrospectionUtilsImpl.isPathVariableParameter(method, 0));

    }

    @Test
    public void getPathVariableName() throws Exception {

        assertEquals("path-param", springHateoasIntrospectionUtilsImpl.getPathVariableName(method, 1));

    }

    @Test
    public void isQueryVariableParameterTrue() throws Exception {

        assertTrue(springHateoasIntrospectionUtilsImpl.isQueryVariableParameter(method, 0));

    }

    @Test
    public void isQueryVariableParameterFalse() throws Exception {

        assertFalse(springHateoasIntrospectionUtilsImpl.isQueryVariableParameter(method, 1));

    }

    @Test
    public void getQueryVariableName() throws Exception {

        assertEquals("query-param", springHateoasIntrospectionUtilsImpl.getQueryVariableName(method, 0));

    }

    @Test
    public void isRequestBodyVariableParameterTrue() throws Exception {

        assertTrue(springHateoasIntrospectionUtilsImpl.isRequestBodyVariableParameter(method, 2));

    }

    @Test
    public void isRequestBodyVariableParameterFalse() throws Exception {

        assertFalse(springHateoasIntrospectionUtilsImpl.isRequestBodyVariableParameter(method, 1));

    }

    @Test
    public void haveToGenerateTemplateForNonAnnotatedMethod() throws Exception {
        assertFalse(springHateoasIntrospectionUtilsImpl.haveToGenerateTemplateFor(nonAnnotatedMethodGenerateUriTemplateFor));
    }

    @Test
    public void haveToGenerateTemplateForAnnotatedMethod() throws Exception {
        assertTrue(springHateoasIntrospectionUtilsImpl.haveToGenerateTemplateFor(annotatedMethodGenerateUriTemplateFor));
    }

    @Test
    public void haveToGenerateTemplateForComposedAnnotatedMethod() throws Exception {
        assertTrue(springHateoasIntrospectionUtilsImpl.haveToGenerateTemplateFor(composedAnnotatedMethodGenerateUriTemplateFor));
    }

    @Test
    public void isRestControllerTrue() throws Exception {
        Controller controller = new Controller();

        assertTrue(springHateoasIntrospectionUtilsImpl.isRestController(controller));
    }

    @Test
    public void isRestControllerFalse() throws Exception {
        Object object = new Object();

        assertFalse(springHateoasIntrospectionUtilsImpl.isRestController(object));
    }

    @Test
    public void hasComposedAnnotationNonAnnotatedMethod() throws Exception {
        assertFalse(springHateoasIntrospectionUtilsImpl.hasComposedAnnotation(nonAnnotatedMethodComposedAnnotationClass,
                MyAnnotation.class));
    }

    @Test
    public void hasComposedAnnotationForAnnotatedMethod() throws Exception {
        assertTrue(springHateoasIntrospectionUtilsImpl.hasComposedAnnotation(annotatedMethodComposedAnnotationClass,
                MyAnnotation.class));
    }

    @Test
    public void hasComposedAnnotationForComposedAnnotatedMethod() throws Exception {
        assertTrue(springHateoasIntrospectionUtilsImpl.hasComposedAnnotation(composedAnnotatedMethodComposedAnnotationClass,
                MyAnnotation.class));
    }

    @Test(expected = LinkBuilderException.class)
    public void getComposedAnnotationNonAnnotatedMethod() throws Exception {
        MyAnnotation myAnnotation = springHateoasIntrospectionUtilsImpl.
                getComposedAnnotation(nonAnnotatedMethodComposedAnnotationClass, MyAnnotation.class);

        assertThat(myAnnotation, is(nullValue()));
    }

    @Test
    public void getComposedAnnotationForAnnotatedMethod() throws Exception {
        Annotation annotation = springHateoasIntrospectionUtilsImpl.
                getComposedAnnotation(annotatedMethodComposedAnnotationClass, MyAnnotation.class);

        assertThat(annotation, is(notNullValue()));
        assertThat(annotation.getClass(), is(typeCompatibleWith(MyAnnotation.class)));
    }

    @Test
    public void getComposedAnnotationForComposedAnnotatedMethod() throws Exception {
        Annotation annotation = springHateoasIntrospectionUtilsImpl.
                getComposedAnnotation(composedAnnotatedMethodComposedAnnotationClass, MyAnnotation.class);


        assertThat(annotation, is(notNullValue()));
        assertThat(annotation.getClass(), is(typeCompatibleWith(MyComposedAnnotation.class)));
    }

    @Test
    public void getAnnotatedMethod() {
        GenerateUriTemplateForClass bean = new GenerateUriTemplateForClass();

        Set<Method> annotatedMethods = springHateoasIntrospectionUtilsImpl.getAnnotatedMethods(bean,
                GenerateUriTemplateFor.class);

        assertThat(annotatedMethods, hasSize(2));
        assertThat(annotatedMethods, hasItems(annotatedMethodGenerateUriTemplateFor,
                composedAnnotatedMethodGenerateUriTemplateFor));

    }


    @Test
    public void isEnableSelfFromCurrentCallMethodTrue() throws Exception {
        Controller controller = new Controller();

        assertTrue(springHateoasIntrospectionUtilsImpl.isEnableSelfFromCurrentCallMethod(annotatedMethodEnableSelfFromCurrentCall));
    }

    @Test
    public void isEnableSelfFromCurrentCallMethodFalse() throws Exception {
        Object object = new Object();

        assertFalse(springHateoasIntrospectionUtilsImpl.isEnableSelfFromCurrentCallMethod(nonAnnotatedMethodEnableSelfFromCurrentCall));
    }


    @Test
    public void getMethodRelAnnotatedMethod() {

        assertThat(springHateoasIntrospectionUtilsImpl.getMethodRel(annotatedMethodGenerateUriTemplateFor), is("any-rel"));

    }

    @Test
    public void getMethodRelNonAnnotatedMethod() {

        assertThat(springHateoasIntrospectionUtilsImpl.getMethodRel(nonAnnotatedMethodGenerateUriTemplateFor), is(nullValue()));

    }

    @Test
    public void getMethodRelComposedAnnotatedMethod() {

        assertThat(springHateoasIntrospectionUtilsImpl.getMethodRel(composedAnnotatedMethodGenerateUriTemplateFor), is(nullValue()));

    }


    @Test
    public void getMethodDestinationNonAnnotatedMethod() {
        assertThat(springHateoasIntrospectionUtilsImpl.getMethodDestination(nonAnnotatedMethodGenerateUriTemplateForDestinationClass),
                is(nullValue()));

    }

    @Test
    public void getMethodDestinationAnnotatedMethod() {
        assertThat(springHateoasIntrospectionUtilsImpl.getMethodDestination(annotatedMethodGenerateUriTemplateForDestinationClass),
                is("any-destination"));

    }

    @Test
    public void getMethodDestinationComposedAnnotatedMethod() {
        assertThat(springHateoasIntrospectionUtilsImpl.getMethodDestination(composedAnnotatedMethodGenerateUriTemplateForDestinationClass),
                is("any-composed-destination"));

    }



    public void method(
            @RequestParam("query-param") String requestParam,
            @PathVariable("path-param") String pathParam,
            @RequestBody String requestBody) {
    }

    public static class GenerateUriTemplateForClass {

        public void nonAnnotatedMethod() {
        }

        @GenerateUriTemplateFor(rel = "any-rel")
        public void annotatedMethod() {
        }

        @MyGenerateUriTemplateFor(rel = "any-composed-rel")
        public void composedAnnotatedMethod() {

        }
    }


    public static class GenerateUriTemplateForDestinationClass {

        public void nonAnnotatedMethod() {
        }

        @GenerateUriTemplateFor(destination = "any-destination")
        public void annotatedMethod() {
        }

        @MyGenerateUriTemplateFor(destination = "any-composed-destination")
        public void composedAnnotatedMethod() {

        }
    }


    @GenerateUriTemplateFor
    @Target({ElementType.METHOD, ElementType.TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    public static @interface MyGenerateUriTemplateFor {

        String rel() default  "";

        String destination() default "";

    }

    @RestController
    public static class Controller {

        public void controllerMethod() {

        }
    }


    public static class EnableSelfFromCurrentCallClass {

        public void nonAnnotatedMethod() {
        }

        @EnableSelfFromCurrentCall
        public void annotatedMethod() {
        }

    }

    @Target({ElementType.METHOD,ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    public static @interface MyAnnotation {

    }

    @MyAnnotation
    @Target({ElementType.METHOD,ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    public static @interface MyComposedAnnotation {

    }

    public static class ComposedAnnotationClass {

        @MyAnnotation
        public void annotatedMethod() {

        }

        @MyComposedAnnotation
        public void composedAnnotatedMethod() {

        }

        public void nonAnnotatedMethod() {

        }

    }


}