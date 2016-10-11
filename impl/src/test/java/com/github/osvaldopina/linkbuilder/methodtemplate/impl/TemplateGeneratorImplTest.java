package com.github.osvaldopina.linkbuilder.methodtemplate.impl;

import com.damnhandy.uri.template.UriTemplate;
import com.github.osvaldopina.linkbuilder.LinkBuilderException;
import com.github.osvaldopina.linkbuilder.methodtemplate.impl.TemplateGeneratorImpl;
import com.github.osvaldopina.linkbuilder.methodtemplate.impl.TemplatePathDiscover;
import org.easymock.*;
import com.github.osvaldopina.linkbuilder.argumentresolver.ArgumentResolver;
import com.github.osvaldopina.linkbuilder.argumentresolver.ArgumentResolvers;
import com.github.osvaldopina.linkbuilder.utils.UriTemplateAugmenter;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.core.MethodParameter;
import org.springframework.hateoas.core.MethodParameters;
import org.springframework.web.bind.annotation.RequestParam;

import java.lang.reflect.Method;
import java.util.Arrays;

import static org.junit.Assert.*;

@Ignore
public class TemplateGeneratorImplTest extends EasyMockSupport {

    @Rule
    public EasyMockRule mocks = new EasyMockRule(this);

    @TestSubject
    TemplateGeneratorImpl templateGeneratorImpl = new TemplateGeneratorImpl();

    @Mock
    private TemplatePathDiscover templatePathDiscover;

    @Mock
    private UriTemplateAugmenter.Factory uriTemplateAugmenterFactory;

    @Mock
    private UriTemplateAugmenter uriTemplateAugmenter;


    @Mock
    private TemplateGeneratorImpl.MethodParametersFactory methodParametersFactory;

    @Mock
    private ArgumentResolvers argumentResolvers;

    @Mock
    private ArgumentResolver argumentResolver;

    @Mock
    private MethodParameters methodParameters;

    private Method method;

    private int parameterIndex =0;

    @Mock
    private UriTemplate uriTemplate;

    @Before
    public void setUp() throws Exception {
        method = Request.class.getMethod("method", String.class);
    }

    @Test
    public void generate() {
        EasyMock.expect(uriTemplateAugmenterFactory.create()).andReturn(uriTemplateAugmenter);
        templatePathDiscover.augmentPath(uriTemplateAugmenter, method);
        EasyMock.expectLastCall();
        EasyMock.expect(argumentResolvers.getArgumentResolverFor(method, parameterIndex)).andReturn(argumentResolver);
        argumentResolver.augmentTemplate(uriTemplateAugmenter, method, parameterIndex);
        EasyMock.expectLastCall();
        EasyMock.expect(uriTemplateAugmenter.getUriTemplate()).andReturn(uriTemplate);

        replayAll();

        assertSame(uriTemplate, templateGeneratorImpl.generate(method));

        verifyAll();

    }

    @Test(expected = LinkBuilderException.class)
    public void generateArgumentResolverNull() throws Exception {
        EasyMock.expect(uriTemplateAugmenterFactory.create()).andReturn(uriTemplateAugmenter);
        templatePathDiscover.augmentPath(uriTemplateAugmenter, method);
        EasyMock.expectLastCall();
        EasyMock.expect(argumentResolvers.getArgumentResolverFor(method, parameterIndex)).andThrow(new LinkBuilderException("Error"));

        replayAll();


        assertSame(uriTemplate, templateGeneratorImpl.generate(method));

        verifyAll();

    }

    public static class Request {

        public void method(@RequestParam("query1") String method) {

        }

    }




}