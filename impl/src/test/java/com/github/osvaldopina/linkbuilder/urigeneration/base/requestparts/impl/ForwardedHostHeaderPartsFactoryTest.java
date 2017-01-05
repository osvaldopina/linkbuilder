package com.github.osvaldopina.linkbuilder.urigeneration.base.requestparts.impl;

import com.github.osvaldopina.linkbuilder.urigeneration.base.requestparts.impl.ChainedRequestParts;
import com.github.osvaldopina.linkbuilder.urigeneration.base.requestparts.impl.ForwardedHostHeaderPartsFactory;
import org.easymock.*;
import org.junit.Rule;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class ForwardedHostHeaderPartsFactoryTest extends EasyMockSupport {

    @Rule
    public EasyMockRule mocks = new EasyMockRule(this);

    @TestSubject
    ForwardedHostHeaderPartsFactory forwardedHostHeaderPartsFactory = new ForwardedHostHeaderPartsFactory();

    @Mock
    HttpServletRequest httpServletRequest;

    @Mock
    Enumeration<String> headers;

    @Mock
    ChainedRequestParts nextChainedRequestParts;

    @Test
    public void createEmptyHeader() throws Exception {
        EasyMock.expect(httpServletRequest.getHeader("Forwarded")).andReturn(null);
        EasyMock.expect(nextChainedRequestParts.getScheme()).andReturn(null);
        EasyMock.expect(nextChainedRequestParts.getHost()).andReturn(null);
        EasyMock.expect(nextChainedRequestParts.getPort()).andReturn(-1);
        EasyMock.expect(nextChainedRequestParts.getContextPath()).andReturn(null);

        replayAll();

        ChainedRequestParts  chainedRequestParts = forwardedHostHeaderPartsFactory.create(httpServletRequest);
        chainedRequestParts.setNext(nextChainedRequestParts);

        assertNull(chainedRequestParts.getScheme());
        assertNull(chainedRequestParts.getHost());
        assertEquals(-1, chainedRequestParts.getPort());
        assertNull(chainedRequestParts.getContextPath());

        verifyAll();


    }

    @Test
    public void create() throws Exception {
        EasyMock.expect(httpServletRequest.getHeader("Forwarded")).andReturn("Forwarded: for=192.0.2.60; proto=http; host=localhost");
        EasyMock.expect(httpServletRequest.getHeaders("Forwarded")).andReturn(headers);
        EasyMock.expect(headers.nextElement()).andReturn("for=192.0.2.60; proto=http; host=localhost");
        EasyMock.expect(nextChainedRequestParts.getPort()).andReturn(-1);
        EasyMock.expect(nextChainedRequestParts.getContextPath()).andReturn(null);

        replayAll();

        ChainedRequestParts  chainedRequestParts = forwardedHostHeaderPartsFactory.create(httpServletRequest);
        chainedRequestParts.setNext(nextChainedRequestParts);

        assertEquals("http" , chainedRequestParts.getScheme());
        assertEquals("localhost", chainedRequestParts.getHost());
        assertEquals(-1, chainedRequestParts.getPort());
        assertNull(chainedRequestParts.getContextPath());

        verifyAll();


    }

}