package com.github.osvaldopina.linkbuilder.expression.impl;

import org.springframework.security.web.FilterInvocation;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.FilterChain;

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
