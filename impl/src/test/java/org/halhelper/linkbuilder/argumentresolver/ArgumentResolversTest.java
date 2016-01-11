package org.halhelper.linkbuilder.argumentresolver;

import com.damnhandy.uri.template.UriTemplate;
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

    @Test
    public void getArgumentResolverForWithoutArgumentResolver() throws Exception {

        argumentResolvers = new ArgumentResolvers(Arrays.asList(argumentResolver));

        EasyMock.expect(argumentResolver.resolveFor(methodParameter)).andReturn(false);

        replayAll();

        assertNull(argumentResolvers.getArgumentResolverFor(methodParameter));

        verifyAll();

    }

}