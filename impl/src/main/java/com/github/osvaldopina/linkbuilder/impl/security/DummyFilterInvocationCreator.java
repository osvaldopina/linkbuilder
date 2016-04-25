package com.github.osvaldopina.linkbuilder.impl.security;

import org.springframework.security.web.FilterInvocation;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

public class DummyFilterInvocationCreator {

    public FilterInvocation create() {

        ServletRequestAttributes servletRequestAttributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        FilterChain filterChain = new DummyFilterChain();

        return new FilterInvocation(
                servletRequestAttributes.getRequest(),
                servletRequestAttributes.getResponse(),
                filterChain
        );

    }
}
