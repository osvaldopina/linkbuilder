package com.github.osvaldopina.linkbuilder.argumentresolver;

import com.github.osvaldopina.linkbuilder.LinkBuilderException;
import org.easymock.*;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.core.MethodParameter;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by deinf.osvaldo on 28/12/2015.
 */
public class ArgumentResolversTest  extends EasyMockSupport {

    @Rule
    public EasyMockRule mocks = new EasyMockRule(this);

    @Mock
    private ArgumentResolver argumentResolver;

    @Mock
    private MethodParameter methodParameter;

    private ArgumentResolvers argumentResolvers;

    @Test
    public void getArgumentResolverForWithAArgumentResolver() throws Exception {

        argumentResolvers = new ArgumentResolvers(Arrays.asList(argumentResolver));

        EasyMock.expect(argumentResolver.resolveFor(methodParameter)).andReturn(true);

        replayAll();

        assertSame(argumentResolver, argumentResolvers.getArgumentResolverFor(methodParameter));

        verifyAll();

    }

    @Test(expected = LinkBuilderException.class)
    public void getArgumentResolverForWithoutArgumentResolver() throws Exception {

        argumentResolvers = new ArgumentResolvers(Arrays.asList(argumentResolver));

        EasyMock.expect(argumentResolver.resolveFor(methodParameter)).andReturn(false);
        // Information for throwing exception
        EasyMock.expect(methodParameter.getParameterIndex()).andReturn(1);
        EasyMock.expect(methodParameter.getParameterType()).andReturn((Class) String.class);
        EasyMock.expect(methodParameter.getMethod()).andReturn(ArgumentResolversTest.class.getMethod("getArgumentResolverForWithoutArgumentResolver"));

        replayAll();

        assertNull(argumentResolvers.getArgumentResolverFor(methodParameter));

        verifyAll();

    }
}