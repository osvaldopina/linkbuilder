package com.github.osvaldopina.linkbuilder.utils;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;




public class UtilsTest {

    private Utils utils;

    @Before
    public void setUp() {
        utils = new Utils();
    }

    @Test
    public void isPresent() {
        assertThat(utils.isPresent("value"), is(true));
    }

    @Test
    public void isPresent_emptyString() {
        assertThat(utils.isPresent(""), is(false));
    }

    @Test
    public void isPresent_nullValueemptyString() {
        assertThat(utils.isPresent(null), is(false));
    }

    @Test
    public void isPresent_anyObject() {
        assertThat(utils.isPresent(new Object()), is(true));
    }
}