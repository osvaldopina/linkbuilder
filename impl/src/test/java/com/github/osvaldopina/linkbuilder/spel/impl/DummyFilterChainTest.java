package com.github.osvaldopina.linkbuilder.spel.impl;

import com.github.osvaldopina.linkbuilder.spel.impl.DummyFilterChain;
import org.junit.Test;

public class DummyFilterChainTest {

    DummyFilterChain dummyFilterChain;

    @Test(expected = UnsupportedOperationException.class)
    public void doFilter() throws Exception {

        dummyFilterChain = new DummyFilterChain();

        dummyFilterChain.doFilter(null, null);

    }
}