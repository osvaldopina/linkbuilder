package com.github.osvaldopina.linkbuilder.methodtemplate.uridiscover.requestparts.impl;

import com.github.osvaldopina.linkbuilder.methodtemplate.uridiscover.requestparts.RequestPartsFactory;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class RequestPartsFactoryListImplTest {

    private RequestPartsFactoryListImpl requestPartsFactoryListImpl;

    @Test
    public void testGetRequestPartsFactories() throws Exception {
        requestPartsFactoryListImpl = new RequestPartsFactoryListImpl();

        List<RequestPartsFactory> requestPartsFactories = requestPartsFactoryListImpl.getRequestPartsFactories();

        assertNotNull(requestPartsFactories);
        assertEquals(5, requestPartsFactories.size());
        assertEquals(XForwardedProtoHeaderPartsFactory.class,  requestPartsFactories.get(0).getClass());
        assertEquals(XForwardedPortHeaderPartsFactory.class,  requestPartsFactories.get(1).getClass());
        assertEquals(XForwardedHostHeaderPartsFactory.class,  requestPartsFactories.get(2).getClass());
        assertEquals(ForwardedHostHeaderPartsFactory.class,  requestPartsFactories.get(3).getClass());
        assertEquals(ServletRequestRequestPartsFactory.class,  requestPartsFactories.get(4).getClass());
    }
}