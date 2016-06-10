package com.github.osvaldopina.linkbuilder.methodtemplate.uridiscover.requestparts.impl;

import org.easymock.*;
import org.junit.Rule;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

import static org.junit.Assert.*;

public class ServletRequestRequestPartsFactoryTest  extends EasyMockSupport {

    @Rule
    public EasyMockRule mocks = new EasyMockRule(this);

    @TestSubject
    ServletRequestRequestPartsFactory servletRequestRequestPartsFactory = new ServletRequestRequestPartsFactory();

    @Mock
    HttpServletRequest httpServletRequest;

    StringBuffer url;

    @Mock
    ChainedRequestParts nextChainedRequestParts;

    @Test
    public void create() throws Exception {
        url = new StringBuffer("http://localhost:8080/path");


        EasyMock.expect(httpServletRequest.getRequestURL()).andReturn(url);
        EasyMock.expect(httpServletRequest.getContextPath()).andReturn("path");

        replayAll();

        ChainedRequestParts  chainedRequestParts = servletRequestRequestPartsFactory.create(httpServletRequest);
        chainedRequestParts.setNext(nextChainedRequestParts);

        assertEquals("http",chainedRequestParts.getScheme());
        assertEquals("localhost", chainedRequestParts.getHost());
        assertEquals(8080, chainedRequestParts.getPort());
        assertEquals("path" , chainedRequestParts.getContextPath());

        verifyAll();


    }


}