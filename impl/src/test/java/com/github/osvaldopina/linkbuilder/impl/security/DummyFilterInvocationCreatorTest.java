package com.github.osvaldopina.linkbuilder.impl.security;

import org.easymock.EasyMockRule;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.security.web.FilterInvocation;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertSame;

public class DummyFilterInvocationCreatorTest extends EasyMockSupport {

        @Rule
        public EasyMockRule mocks = new EasyMockRule(this);

        @Mock
        private HttpServletRequest request;

        @Mock
        private HttpServletResponse response;

        private DummyFilterInvocationCreator dummyFilterInvocationCreator;


    @Test
    public void create() throws Exception {

        ServletRequestAttributes servletRequestAttributes = new ServletRequestAttributes(request, response);

        RequestContextHolder.setRequestAttributes(servletRequestAttributes);

        dummyFilterInvocationCreator = new DummyFilterInvocationCreator();

        FilterInvocation filterInvocation = dummyFilterInvocationCreator.create();

        assertSame(request, filterInvocation.getRequest());
        assertSame(response, filterInvocation.getResponse());
        assertEquals(DummyFilterChain.class, filterInvocation.getChain().getClass());
    }
}