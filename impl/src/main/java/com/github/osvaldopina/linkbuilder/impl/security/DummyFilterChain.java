package com.github.osvaldopina.linkbuilder.impl.security;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

public class DummyFilterChain implements FilterChain {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse)
            throws IOException, ServletException {
        throw new UnsupportedOperationException("It is not supposed to be called!");
    }
}
