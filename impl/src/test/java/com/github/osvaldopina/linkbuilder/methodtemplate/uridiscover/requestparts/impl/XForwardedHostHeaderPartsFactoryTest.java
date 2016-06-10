package com.github.osvaldopina.linkbuilder.methodtemplate.uridiscover.requestparts.impl;

import org.easymock.*;
import org.junit.Rule;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class XForwardedHostHeaderPartsFactoryTest extends EasyMockSupport {

    @Rule
    public EasyMockRule mocks = new EasyMockRule(this);

    @TestSubject
    XForwardedHostHeaderPartsFactory xForwardedHostHeaderPartsFactory = new XForwardedHostHeaderPartsFactory();

    @Mock
    HttpServletRequest httpServletRequest;

    @Mock
    Enumeration<String> headers;

    @Mock
    ChainedRequestParts nextChainedRequestParts;

    @Test
    public void createEmptyHeader() throws Exception {
        EasyMock.expect(httpServletRequest.getHeader("X-Forwarded-Host")).andReturn(null);
        EasyMock.expect(nextChainedRequestParts.getScheme()).andReturn(null);
        EasyMock.expect(nextChainedRequestParts.getHost()).andReturn(null);
        EasyMock.expect(nextChainedRequestParts.getPort()).andReturn(-1);
        EasyMock.expect(nextChainedRequestParts.getContextPath()).andReturn(null);

        replayAll();

        ChainedRequestParts  chainedRequestParts = xForwardedHostHeaderPartsFactory.create(httpServletRequest);
        chainedRequestParts.setNext(nextChainedRequestParts);

        assertNull(chainedRequestParts.getScheme());
        assertNull(chainedRequestParts.getHost());
        assertEquals(-1, chainedRequestParts.getPort());
        assertNull(chainedRequestParts.getContextPath());

        verifyAll();


    }

    @Test
    public void create() throws Exception {
        EasyMock.expect(httpServletRequest.getHeader("X-Forwarded-Host")).andReturn("X-Forwarded-Host: host:8080");
        EasyMock.expect(httpServletRequest.getHeaders("X-Forwarded-Host")).andReturn(headers);
        EasyMock.expect(headers.nextElement()).andReturn("host:8080");
        EasyMock.expect(nextChainedRequestParts.getScheme()).andReturn(null);
        EasyMock.expect(nextChainedRequestParts.getContextPath()).andReturn(null);

        replayAll();

        ChainedRequestParts  chainedRequestParts = xForwardedHostHeaderPartsFactory.create(httpServletRequest);
        chainedRequestParts.setNext(nextChainedRequestParts);

        assertNull(chainedRequestParts.getScheme());
        assertEquals("host", chainedRequestParts.getHost());
        assertEquals(8080, chainedRequestParts.getPort());
        assertNull(chainedRequestParts.getContextPath());

        verifyAll();


    }

}