package com.github.osvaldopina.linkbuilder.expression.springhateoas;

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
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

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

        assertThat(filterInvocation.getRequest(), is(sameInstance(request)));
        assertThat(filterInvocation.getResponse(), is(sameInstance(response)));
        assertThat(filterInvocation.getChain().getClass(), is(typeCompatibleWith(DummyFilterChain.class)));
    }
}