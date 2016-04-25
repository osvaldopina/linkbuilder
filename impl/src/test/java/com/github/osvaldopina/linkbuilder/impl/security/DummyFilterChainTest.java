package com.github.osvaldopina.linkbuilder.impl.security;

import org.junit.Test;

import javax.naming.OperationNotSupportedException;

import static org.junit.Assert.*;

public class DummyFilterChainTest {

    DummyFilterChain dummyFilterChain;

    @Test(expected = UnsupportedOperationException.class)
    public void doFilter() throws Exception {

        dummyFilterChain = new DummyFilterChain();

        dummyFilterChain.doFilter(null, null);

    }
}