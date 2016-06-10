package com.github.osvaldopina.linkbuilder.methodtemplate.uridiscover.requestparts.impl;

import com.github.osvaldopina.linkbuilder.methodtemplate.uridiscover.requestparts.RequestParts;
import org.easymock.EasyMock;
import org.easymock.EasyMockRule;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ChainedRequestPartsTest extends EasyMockSupport {

    @Rule
    public EasyMockRule mocks = new EasyMockRule(this);

    @Mock
    RequestParts next;

    ChainedRequestParts chainedRequestParts;

    @Test
    public void getSchemeCurrentRequestPartsHasValue() throws Exception {
        chainedRequestParts = new ChainedRequestParts("scheme", null, -1, null);

        chainedRequestParts.setNext(next);

        replayAll();

        assertEquals("scheme", chainedRequestParts.getScheme());

        verifyAll();

    }

    @Test
    public void getSchemeNextRequestPartsHasValue() throws Exception {
        EasyMock.expect(next.getScheme()).andReturn("scheme-next");


        chainedRequestParts = new ChainedRequestParts(null, null, -1, null);

        chainedRequestParts.setNext(next);

        replayAll();

        assertEquals("scheme-next", chainedRequestParts.getScheme());

        verifyAll();

    }

    @Test
    public void getHostCurrentRequestPartsHasValue() throws Exception {
        chainedRequestParts = new ChainedRequestParts(null, "host", -1, null);

        chainedRequestParts.setNext(next);

        replayAll();

        assertEquals("host", chainedRequestParts.getHost());

        verifyAll();

    }

    @Test
    public void getHostNextRequestPartsHasValue() throws Exception {
        EasyMock.expect(next.getHost()).andReturn("host-next");


        chainedRequestParts = new ChainedRequestParts(null, null, -1, null);

        chainedRequestParts.setNext(next);

        replayAll();

        assertEquals("host-next", chainedRequestParts.getHost());

        verifyAll();

    }

    @Test
    public void getPortCurrentRequestPartsHasValue() throws Exception {
        chainedRequestParts = new ChainedRequestParts(null, null, 8080, null);

        chainedRequestParts.setNext(next);

        replayAll();

        assertEquals(8080, chainedRequestParts.getPort());

        verifyAll();

    }

    @Test
    public void getPortNextRequestPartsHasValue() throws Exception {
        EasyMock.expect(next.getPort()).andReturn(8080);


        chainedRequestParts = new ChainedRequestParts(null, null, -1, null);

        chainedRequestParts.setNext(next);

        replayAll();

        assertEquals(8080, chainedRequestParts.getPort());

        verifyAll();

    }

    @Test
    public void getContextPathNextRequestPartsHasValue() throws Exception {
        EasyMock.expect(next.getContextPath()).andReturn("context-path-next");


        chainedRequestParts = new ChainedRequestParts(null, null, -1, null);

        chainedRequestParts.setNext(next);

        replayAll();

        assertEquals("context-path-next", chainedRequestParts.getContextPath());

        verifyAll();

    }

    @Test
    public void getContextPathCurrentRequestPartsHasValue() throws Exception {

        chainedRequestParts = new ChainedRequestParts(null, null, 8080, "context-path");

        chainedRequestParts.setNext(next);

        replayAll();

        assertEquals("context-path", chainedRequestParts.getContextPath());

        verifyAll();

    }

    @Test
    public void getUri() throws Exception {

        ChainedRequestParts chainedRequestParts = new ChainedRequestParts("http", "localhost", 8080, "/context-path");
        chainedRequestParts.setNext(next);

        replayAll();

        assertEquals("http://localhost:8080/context-path", chainedRequestParts.getUri());

        verifyAll();

    }

    @Test
    public void getUriNormalizedForHttp() throws Exception {

        ChainedRequestParts chainedRequestParts = new ChainedRequestParts("http", "localhost", 80, "/context-path");
        chainedRequestParts.setNext(next);

        replayAll();

        assertEquals("http://localhost/context-path", chainedRequestParts.getUri());

        verifyAll();

    }

    @Test
    public void getUriNormalizedForHttps() throws Exception {

        ChainedRequestParts chainedRequestParts = new ChainedRequestParts("https", "localhost", 443, "/context-path");
        chainedRequestParts.setNext(next);

        replayAll();

        assertEquals("https://localhost/context-path", chainedRequestParts.getUri());

        verifyAll();

    }
}