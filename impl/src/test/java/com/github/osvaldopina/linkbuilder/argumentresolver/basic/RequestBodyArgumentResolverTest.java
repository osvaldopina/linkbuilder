package com.github.osvaldopina.linkbuilder.argumentresolver.basic;

import com.damnhandy.uri.template.UriTemplate;
import com.damnhandy.uri.template.UriTemplateBuilder;
import com.github.osvaldopina.linkbuilder.argumentresolver.variablesubstitutioncontroller.VariableSubstitutionController;
import com.github.osvaldopina.linkbuilder.utils.IntrospectionUtils;
import com.github.osvaldopina.linkbuilder.utils.UriTemplateAugmenter;
import org.easymock.EasyMock;
import org.easymock.EasyMockRule;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.lang.reflect.Method;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class RequestBodyArgumentResolverTest extends EasyMockSupport {

    @Rule
    public EasyMockRule mocks = new EasyMockRule(this);

    @Mock
    IntrospectionUtils introspectionUtils;

    @TestSubject
    private RequestBodyAnnotationArgumentResolver requestBodyAnnotationArgumentResolver =
            new RequestBodyAnnotationArgumentResolver(introspectionUtils);

    private UriTemplateBuilder uriTemplateBuilder;

    @Mock
    private UriTemplate uriTemplate;

    private Method method;

    @Mock
    private UriTemplateAugmenter.Factory uriTemplateAugmentFactory;

    @Mock
    private UriTemplateAugmenter uriTemplateAugmenter;

    @Mock
    private VariableSubstitutionController variableSubstitutionController;


    @Before
    public void setUp() throws Exception {

        uriTemplateBuilder = UriTemplate.createBuilder();

        method = RequestBodyArgumentResolverTest.class.getMethod("equals", Object.class);
    }


    @Test
    public void resolveForAnnotatedMethod() throws Exception {

        EasyMock.expect(introspectionUtils.isRequestBodyVariableParameter(method, 0)).andReturn(true);

        replayAll();

        assertTrue(requestBodyAnnotationArgumentResolver.resolveFor(method, 0));

        verifyAll();

    }

    @Test
    public void resolveForNonAnnotatedMethod() throws Exception {

        EasyMock.expect(introspectionUtils.isRequestBodyVariableParameter(method, 0)).andReturn(false);

        replayAll();

        assertFalse(requestBodyAnnotationArgumentResolver.resolveFor(method, 0));

        verifyAll();

    }

    @Test
    public void augmentTemplate() throws Exception {

        replayAll();

        requestBodyAnnotationArgumentResolver.augmentTemplate(uriTemplateAugmenter, method, 0);

        verifyAll();
    }

    @Test
    public void setTemplateVariables() throws Exception {

        replayAll();

        requestBodyAnnotationArgumentResolver.setTemplateVariables(uriTemplate, method, 0, "value" , variableSubstitutionController);

        verifyAll();
    }

}

