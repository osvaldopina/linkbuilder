package com.github.osvaldopina.linkbuilder;

import org.easymock.*;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;

import javax.servlet.http.HttpServletRequest;

import static org.junit.Assert.assertEquals;

/**
 * Created by osvaldopina on 1/8/16.
 */
public class BaseUriDiscoverTest  extends EasyMockSupport {

    @Rule
    public EasyMockRule mocks = new EasyMockRule(this);

    @TestSubject
    BaseUriDiscover baseUriDiscover = new BaseUriDiscover();

    @Mock
    HttpServletRequest httpServletRequest;

    @Mock
    ApplicationContext applicationContext;

    @Mock
    Environment environment;

    @Test
    public void getBaseUriNoContextPath() throws Exception {
        StringBuffer url = new StringBuffer("http://localhost:8080/context-path/path?query=value");

        EasyMock.expect(httpServletRequest.getRequestURL()).andReturn(url);
        EasyMock.expect(httpServletRequest.getHeader("Forwarded")).andReturn(null);
        EasyMock.expect(httpServletRequest.getHeader("X-Forwarded-Host")).andReturn(null);
        EasyMock.expect(httpServletRequest.getHeader("X-Forwarded-Port")).andReturn(null);
        EasyMock.expect(httpServletRequest.getHeader("X-Forwarded-Proto")).andReturn(null);
        EasyMock.expect(applicationContext.getEnvironment()).andReturn(environment);
        EasyMock.expect(environment.getProperty("server.contextPath")).andReturn("context-path");

        replayAll();

        assertEquals("http://localhost:8080/context-path", baseUriDiscover.getBaseUri(httpServletRequest, applicationContext));

        verifyAll();

    }

    @Test
    public void getBaseUriWithContextPath() throws Exception {
        StringBuffer url = new StringBuffer("http://localhost:8080/context-path/path?query=value");

        EasyMock.expect(httpServletRequest.getRequestURL()).andReturn(url);
        EasyMock.expect(httpServletRequest.getHeader("Forwarded")).andReturn(null);
        EasyMock.expect(httpServletRequest.getHeader("X-Forwarded-Host")).andReturn(null);
        EasyMock.expect(httpServletRequest.getHeader("X-Forwarded-Port")).andReturn(null);
        EasyMock.expect(httpServletRequest.getHeader("X-Forwarded-Proto")).andReturn(null);
        EasyMock.expect(applicationContext.getEnvironment()).andReturn(environment);
        EasyMock.expect(environment.getProperty("server.contextPath")).andReturn(null);

        replayAll();

        assertEquals("http://localhost:8080", baseUriDiscover.getBaseUri(httpServletRequest, applicationContext));

        verifyAll();

    }

}