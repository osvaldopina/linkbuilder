package com.github.osvaldopina.linkbuilder.methodtemplate.uridiscover.requestparts.impl;

import com.github.osvaldopina.linkbuilder.LinkBuilderException;
import com.github.osvaldopina.linkbuilder.methodtemplate.uridiscover.impl.BaseUriDiscoverImpl;
import com.github.osvaldopina.linkbuilder.methodtemplate.uridiscover.impl.HttpRequestDiscover;
import org.easymock.*;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;

import javax.servlet.http.HttpServletRequest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Enumeration;

import static org.junit.Assert.*;

public class XForwardedProtoHeaderPartsFactoryTest  extends EasyMockSupport {

    @Rule
    public EasyMockRule mocks = new EasyMockRule(this);

    @TestSubject
    XForwardedProtoHeaderPartsFactory xForwardedProtoHeaderPartsFactory = new XForwardedProtoHeaderPartsFactory();

    @Mock
    HttpServletRequest httpServletRequest;

    @Mock
    Enumeration<String> headers;

    @Mock
    ChainedRequestParts nextChainedRequestParts;

    @Test
    public void createEmptyHeader() throws Exception {
        EasyMock.expect(httpServletRequest.getHeader("X-Forwarded-Proto")).andReturn(null);
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
        EasyMock.expect(httpServletRequest.getHeader("X-Forwarded-Proto")).andReturn("X-Forwarded-Proto: https");
        EasyMock.expect(httpServletRequest.getHeaders("X-Forwarded-Proto")).andReturn(headers);
        EasyMock.expect(headers.nextElement()).andReturn("https");
        EasyMock.expect(nextChainedRequestParts.getHost()).andReturn(null);
        EasyMock.expect(nextChainedRequestParts.getPort()).andReturn(-1);
        EasyMock.expect(nextChainedRequestParts.getContextPath()).andReturn(null);

        replayAll();

        ChainedRequestParts  chainedRequestParts = xForwardedProtoHeaderPartsFactory.create(httpServletRequest);
        chainedRequestParts.setNext(nextChainedRequestParts);

        assertEquals("https",chainedRequestParts.getScheme());
        assertNull(chainedRequestParts.getHost());
        assertEquals(-1, chainedRequestParts.getPort());
        assertNull(chainedRequestParts.getContextPath());

        verifyAll();


    }

}