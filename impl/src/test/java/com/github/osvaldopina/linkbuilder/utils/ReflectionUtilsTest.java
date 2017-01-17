package com.github.osvaldopina.linkbuilder.utils;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.sameInstance;
import static org.hamcrest.Matchers.typeCompatibleWith;


public class ReflectionUtilsTest {

    private ReflectionUtils reflectionUtils;

    @Before
    public void setUp() {
        reflectionUtils = new ReflectionUtils();
    }

    @Test
    public void callMethod_oneArg() throws Exception {

        Equals equals = new Equals();

        Object other = new Object();

        Object result = reflectionUtils.callMethod(Boolean.class, equals, "equals", other);

        assertThat(result.getClass(), is(typeCompatibleWith(Boolean.class)));
        Boolean booleanResult = (Boolean) result;
        assertThat(booleanResult, is(true));
        assertThat(Equals.other, is(sameInstance(other)));

    }

    @Test
    public void callMethod_noArgs() throws Exception {

        Equals equals = new Equals();

        Object result = reflectionUtils.callMethod(Boolean.class, equals, "toString");

        assertThat(result.getClass(), is(typeCompatibleWith(String.class)));
        String toStringResult = (String) result;
        assertThat(toStringResult, is("to-string-value"));

    }

    @Test
    public void hasMethod_oneArg() throws Exception {

        Equals equals = new Equals();

        assertThat(reflectionUtils.hasMethod(equals,"equals", Object.class), is(true));

    }

    @Test
    public void hasMethod_noArgs() throws Exception {

        Equals equals = new Equals();

        assertThat(reflectionUtils.hasMethod(equals,"toString"), is(true));

    }

    @Test
    public void hasMethod_methodDoesNotExists() throws Exception {

        Equals equals = new Equals();

        assertThat(reflectionUtils.hasMethod(equals,"methodThatDoesNotExists"), is(false));

    }

    @Test
    public void hasValue_nullVaue() throws Exception {

        Equals equals = new Equals();

        assertThat(reflectionUtils.hasValue(equals, "nullValue"), is(false));

    }

    @Test
    public void hasValue_nonNullVaue() throws Exception {

        Equals equals = new Equals();

        assertThat(reflectionUtils.hasValue(equals, "nonNullValue"), is(true));

    }

    @Test
    public void hasEmptyValue_nullValue() throws Exception {

        Equals equals = new Equals();

        assertThat(reflectionUtils.hasEmptyValue(equals, "nullValue"), is(true));

    }

    @Test
    public void hasEmptyValue_nonNullValue() throws Exception {

        Equals equals = new Equals();

        assertThat(reflectionUtils.hasEmptyValue(equals, "nonNullValue"), is(false));

    }


    public static class Equals {

        private static Object other;

        @Override
        public boolean equals(Object other) {
            Equals.other = other;
            return true;
        }

        @Override
        public String toString() {
            return "to-string-value";
        }

        public Object nullValue() {
            return null;
        }

        public Object nonNullValue() {
            return new Object();
        }
    }
}