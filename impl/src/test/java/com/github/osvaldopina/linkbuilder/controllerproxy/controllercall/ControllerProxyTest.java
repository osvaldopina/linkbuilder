package com.github.osvaldopina.linkbuilder.controllerproxy.controllercall;

import com.github.osvaldopina.linkbuilder.impl.LinkBuilderImpl;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Method;

import static org.junit.Assert.*;

public class ControllerProxyTest {

    private LinkBuilderImpl linkBuilderImpl = new LinkBuilderImpl(null, null, null);

    private Method method;

    private String parameter = "parameter-value";

    private ControllerProxyTest controllerProxyTest;


    @Before
    public void setUp() throws Exception {
        method = ControllerProxyTest.class.getMethod("method", String.class);

        controllerProxyTest = ControllerProxy.createProxy(ControllerProxyTest.class, linkBuilderImpl);

    }

    @Test
    public void verifyProxySetCallToLinkBuilderImpl() {

        controllerProxyTest.method(parameter);

        // cannot use equals on method because aspects...
        assertEquals(method.toString(), linkBuilderImpl.getMethod().toString());
        assertEquals(1, linkBuilderImpl.getParameters().length);
        assertSame(parameter, linkBuilderImpl.getParameters()[0]);

    }

    public void method(String param1) {

    }


}