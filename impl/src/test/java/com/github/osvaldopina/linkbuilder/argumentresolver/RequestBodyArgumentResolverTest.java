package com.github.osvaldopina.linkbuilder.argumentresolver;

import com.damnhandy.uri.template.UriTemplate;
import com.damnhandy.uri.template.UriTemplateBuilder;
import com.github.osvaldopina.linkbuilder.utils.UriTemplateAugmenter;
import org.easymock.*;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by osvaldopina on 1/4/16.
 */
public class RequestBodyArgumentResolverTest extends EasyMockSupport {

    @Rule
    public EasyMockRule mocks = new EasyMockRule(this);

    @TestSubject
    private RequestBodyAnnotationArgumentResolver requestBodyAnnotationArgumentResolver = new RequestBodyAnnotationArgumentResolver();

    private UriTemplateBuilder uriTemplateBuilder;

    @Mock
    private UriTemplate uriTemplate;

    @Mock
    private MethodParameter methodParameter;

    @Mock
    private UriTemplateAugmenter.Factory uriTemplateAugmentFactory;

    @Mock
    private UriTemplateAugmenter uriTemplateAugmenter;

    @Mock
    private RequestParam requestParam;

    @Mock
    private List<String> templatedParamNames;


    @Before
    public void setUp() {

        uriTemplateBuilder = UriTemplate.createBuilder();
    }


    @Test
    public void resolveForAnnotatedMethod() throws Exception {

        EasyMock.expect(methodParameter.hasParameterAnnotation(RequestBody.class)).andReturn(true);

        replayAll();

        assertTrue(requestBodyAnnotationArgumentResolver.resolveFor(methodParameter));

        verifyAll();

    }

    @Test
    public void resolveForNonAnnotatedMethod() throws Exception {

        EasyMock.expect(methodParameter.hasParameterAnnotation(RequestBody.class)).andReturn(false);

        replayAll();

        assertFalse(requestBodyAnnotationArgumentResolver.resolveFor(methodParameter));

        verifyAll();

    }

}