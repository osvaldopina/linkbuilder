package com.github.osvaldopina.linkbuilder.urigeneration.base.requestparts.impl;


import com.github.osvaldopina.linkbuilder.urigeneration.base.requestparts.impl.ChainedRequestParts;
import com.github.osvaldopina.linkbuilder.urigeneration.base.requestparts.impl.XForwardedPortHeaderPartsFactory;
import org.easymock.*;
import org.junit.Rule;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class XForwardedPortHeaderPartsFactoryTest  extends EasyMockSupport {

    @Rule
    public EasyMockRule mocks = new EasyMockRule(this);

    @TestSubject
    XForwardedPortHeaderPartsFactory xForwardedProtoHeaderPartsFactory = new XForwardedPortHeaderPartsFactory();

    @Mock
    HttpServletRequest httpServletRequest;

    @Mock
    Enumeration<String> headers;

    @Mock
    ChainedRequestParts nextChainedRequestParts;

    @Test
    public void createEmptyHeader() throws Exception {
        EasyMock.expect(httpServletRequest.getHeader("X-Forwarded-Port")).andReturn(null);
        EasyMock.expect(nextChainedRequestParts.getScheme()).andReturn(null);
        EasyMock.expect(nextChainedRequestParts.getHost()).andReturn(null);
        EasyMock.expect(nextChainedRequestParts.getPort()).andReturn(-1);
        EasyMock.expect(nextChainedRequestParts.getContextPath()).andReturn(null);

        replayAll();

        ChainedRequestParts  chainedRequestParts = xForwardedProtoHeaderPartsFactory.create(httpServletRequest);
        chainedRequestParts.setNext(nextChainedRequestParts);

        assertNull(chainedRequestParts.getScheme());
        assertNull(chainedRequestParts.getHost());
        assertEquals(-1, chainedRequestParts.getPort());
        assertNull(chainedRequestParts.getContextPath());

        verifyAll();


    }

    @Test
    public void create() throws Exception {
        EasyMock.expect(httpServletRequest.getHeader("X-Forwarded-Port")).andReturn("X-Forwarded-Port: 8080");
        EasyMock.expect(httpServletRequest.getHeaders("X-Forwarded-Port")).andReturn(headers);
        EasyMock.expect(nextChainedRequestParts.getScheme()).andReturn(null);
        EasyMock.expect(headers.nextElement()).andReturn("8080");
        EasyMock.expect(nextChainedRequestParts.getHost()).andReturn(null);
        EasyMock.expect(nextChainedRequestParts.getContextPath()).andReturn(null);

        replayAll();

        ChainedRequestParts  chainedRequestParts = xForwardedProtoHeaderPartsFactory.create(httpServletRequest);
        chainedRequestParts.setNext(nextChainedRequestParts);

        assertNull(chainedRequestParts.getScheme());
        assertNull(chainedRequestParts.getHost());
        assertEquals(8080, chainedRequestParts.getPort());
        assertNull(chainedRequestParts.getContextPath());

        verifyAll();


    }

}
