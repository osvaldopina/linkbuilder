package com.github.osvaldopina.linkbuilder.methodtemplate.templategenerator.impl;

import com.github.osvaldopina.linkbuilder.utils.UriTemplateAugmenter;
import org.easymock.EasyMock;
import org.easymock.EasyMockRule;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.reflect.Method;

public class TemplatePathDiscoverTest extends EasyMockSupport {

    @Rule
    public EasyMockRule mocks = new EasyMockRule(this);

    @TestSubject
    TemplatePathDiscover templatePathDiscover = new TemplatePathDiscover();

    @Mock
    private UriTemplateAugmenter uriTemplateAugmenter;

    private Method method;

    @Before
    public void setUp() throws Exception {
        method = Path.class.getMethod("method");
    }

    @Test
    public void augmentPath() throws Exception {

        uriTemplateAugmenter.template("/path/{x}/other-path/{y}");
        EasyMock.expectLastCall();

        replayAll();

        templatePathDiscover.augmentPath(uriTemplateAugmenter, method);

        verifyAll();
    }

    @RequestMapping("/path/{x}")
    public static class Path {

        @RequestMapping("/other-path/{y}")
        public void method() {

        }

    }
}