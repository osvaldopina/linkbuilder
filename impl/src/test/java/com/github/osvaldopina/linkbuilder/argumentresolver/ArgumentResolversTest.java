package com.github.osvaldopina.linkbuilder.argumentresolver;

import org.easymock.*;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.core.MethodParameter;

import java.lang.reflect.Method;
import java.util.Arrays;

import static org.junit.Assert.*;

public class ArgumentResolversTest  extends EasyMockSupport {

    @Rule
    public EasyMockRule mocks = new EasyMockRule(this);

    @Mock
    private ArgumentResolver argumentResolver;

    private Method method;

    @Mock
    private MethodParameter methodParameter;

    private ArgumentResolvers argumentResolvers;

    @Before
    public void setUp() throws Exception {
        method = ArgumentResolversTest.class.getMethod("equals", Object.class);
    }

    @Test
    public void getArgumentResolverForWithAArgumentResolver() throws Exception {

        argumentResolvers = new ArgumentResolvers(Arrays.asList(argumentResolver));

        EasyMock.expect(argumentResolver.resolveFor(method, 0)).andReturn(true);

        replayAll();

        assertSame(argumentResolver, argumentResolvers.getArgumentResolverFor(method, 0));

        verifyAll();

    }

    @Test
    public void getArgumentResolverForWithoutArgumentResolver() throws Exception {

        argumentResolvers = new ArgumentResolvers(Arrays.asList(argumentResolver));

        EasyMock.expect(argumentResolver.resolveFor(method, 0)).andReturn(false);

        replayAll();

        ArgumentResolver argumentResolver = argumentResolvers.getArgumentResolverFor(method, 0);
        assertNotNull(argumentResolver);
        assertEquals(argumentResolver.getClass(), EmptyArgumentResolver.class);

        verifyAll();

    }
}