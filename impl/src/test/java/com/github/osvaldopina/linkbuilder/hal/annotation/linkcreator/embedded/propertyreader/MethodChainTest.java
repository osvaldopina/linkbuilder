package com.github.osvaldopina.linkbuilder.hal.annotation.linkcreator.embedded.propertyreader;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.Is.is;


public class MethodChainTest {

    private MethodChain methodChain;

    private FirstBean firstBean;

    private SecondBean secondBean;


    @Before
    public void setUp() throws Exception {

        methodChain = new MethodChain(Arrays.asList(
                FirstBean.class.getDeclaredMethod("getSecondBean"),
                SecondBean.class.getDeclaredMethod("getValue")));

    }

    @Test
    public void execCalls() throws Exception {
        firstBean = new FirstBean(new SecondBean("value"));

        assertThat((String) methodChain.execCalls(firstBean), is("value"));

    }

    @Test
    public void execCalls_nullValueInbetween() throws Exception {
        firstBean = new FirstBean();

        assertThat(methodChain.execCalls(firstBean), is(nullValue()));

    }

    public class FirstBean {

        public FirstBean() {

        }

        public FirstBean(SecondBean secondBean) {
            this.secondBean = secondBean;
        }


        private SecondBean secondBean;

        public SecondBean getSecondBean() {
            return secondBean;
        }

        public void setSecondBean(SecondBean secondBean) {
            this.secondBean = secondBean;
        }
    }

    public class SecondBean {

        public SecondBean() {

        }

        public SecondBean(String value) {
            this.value = value;
        }

        private String value;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}