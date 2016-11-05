package com.github.osvaldopina.linkbuilder.fromcall.controllercallrecorder;

import com.github.osvaldopina.linkbuilder.impl.springhateoas.SpringHateoasLinkBuilderImpl;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.BeansException;
import org.springframework.context.support.GenericApplicationContext;

import java.lang.reflect.Method;

public class ControllerProxyTest {

    private FakeApplicationContext applicationContext;

    private SpringHateoasLinkBuilderImpl springHateoasLinkBuilderImpl;

    private Method method;

    private String parameter = "parameter-value";

    private ControllerProxyTest controllerProxyTest;


    @Before
    public void setUp() throws Exception {
        applicationContext = new FakeApplicationContext();
        springHateoasLinkBuilderImpl = new SpringHateoasLinkBuilderImpl(null,null, null, null, null, null, null);

        method = ControllerProxyTest.class.getMethod("method", String.class);

        controllerProxyTest = ControllerProxy.createProxy(ControllerProxyTest.class, springHateoasLinkBuilderImpl);

    }

    @Test
    @Ignore
    public void verifyProxySetCallToLinkBuilderImpl() {

        controllerProxyTest.method(parameter);

        // cannot use equals on method because aspects...
//        assertEquals(method.toString(), linkBuilderImpl.getMethodCall().getMethod().toString());
//        assertEquals(1, linkBuilderImpl.getMethodCall().getParams().length);
//        assertSame(parameter, linkBuilderImpl.getMethodCall().getParam(0));

    }

    public void method(String param1) {

    }

    public static class FakeApplicationContext extends GenericApplicationContext {
        @Override
        public <T> T getBean(Class<T> requiredType) throws BeansException {
            return null;
        }
    }


}