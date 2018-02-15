package com.github.osvaldopina.linkbuilder.methodtemplate.uridiscover.impl;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

import static org.junit.Assert.assertEquals;

public class HttpRequestDiscoverTest {

    HttpRequestDiscover httpRequestDiscover = new HttpRequestDiscover();

    private HttpServletRequest httpServletRequest;

    private ServletRequestAttributes servletRequestAttributes;


    @Before
    public void setUp() {

        httpServletRequest = EasyMock.createMock(HttpServletRequest.class);

        servletRequestAttributes = new ServletRequestAttributes(httpServletRequest);

        RequestContextHolder.setRequestAttributes(servletRequestAttributes);
    }

    @Test
    public void getCurrentRequest() throws Exception {

        assertEquals(httpServletRequest, httpRequestDiscover.getCurrentRequest());

    }

}