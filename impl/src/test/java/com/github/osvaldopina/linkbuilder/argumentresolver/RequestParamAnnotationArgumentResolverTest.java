package com.github.osvaldopina.linkbuilder.argumentresolver;

import com.damnhandy.uri.template.UriTemplate;
import com.damnhandy.uri.template.UriTemplateBuilder;
import com.github.osvaldopina.linkbuilder.argumentresolver.basic.RequestParamAnnotationArgumentResolver;
import com.github.osvaldopina.linkbuilder.argumentresolver.variablesubstitutioncontroller.VariableSubstitutionController;
import com.github.osvaldopina.linkbuilder.utils.UriTemplateAugmenter;
import org.easymock.*;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static org.junit.Assert.*;

public class RequestParamAnnotationArgumentResolverTest extends EasyMockSupport {

    @Rule
    public EasyMockRule mocks = new EasyMockRule(this);

    @TestSubject
    private RequestParamAnnotationArgumentResolver requestParamAnnotationArgumentResolver = new RequestParamAnnotationArgumentResolver();

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
    private VariableSubstitutionController variableSubstitutionController;


    @Before
    public void setUp() {

        uriTemplateBuilder = UriTemplate.createBuilder();
    }


    @Test
    public void resolveForAnnotatedMethod() throws Exception {

        EasyMock.expect(methodParameter.hasParameterAnnotation(RequestParam.class)).andReturn(true);

        replayAll();

        assertTrue(requestParamAnnotationArgumentResolver.resolveFor(methodParameter));

        verifyAll();

    }

    @Test
    public void resolveForNonAnnotatedMethod() throws Exception {

        EasyMock.expect(methodParameter.hasParameterAnnotation(RequestParam.class)).andReturn(false);

        replayAll();

        assertFalse(requestParamAnnotationArgumentResolver.resolveFor(methodParameter));

        verifyAll();

    }

    @Test
    public void augmentTemplate() throws Exception {
        String varName = "var1";

        EasyMock.expect(methodParameter.getParameterAnnotation(RequestParam.class)).andReturn(requestParam);
        EasyMock.expect(requestParam.value()).andReturn(varName);
        uriTemplateAugmenter.addToQuery(varName);
        EasyMock.expectLastCall();

        replayAll();

        requestParamAnnotationArgumentResolver.augmentTemplate(uriTemplateAugmenter, methodParameter);

        verifyAll();



    }

    @Test
    public void setTemplateVariablesParamNameNotInTemplatedParamNames() throws Exception {
        String varName = "var1";
        String value = "value-for-var1";

        EasyMock.expect(requestParam.value()).andReturn(varName);
        EasyMock.expect(variableSubstitutionController.substitute(methodParameter, "var1", value)).andReturn(true);
        EasyMock.expect(methodParameter.getParameterAnnotation(RequestParam.class)).andReturn(requestParam);
        EasyMock.expect(requestParam.value()).andReturn(varName);
        EasyMock.expect(uriTemplate.set(varName, value)).andReturn(uriTemplate);

        replayAll();

        requestParamAnnotationArgumentResolver.setTemplateVariables(uriTemplate, methodParameter, value, variableSubstitutionController);

        verifyAll();

    }

    @Test
    public void setTemplateVariablesParamNameInTemplatedParamNames() throws Exception {
        String varName = "var1";
        String value = "value-for-var1";

        EasyMock.expect(methodParameter.getParameterAnnotation(RequestParam.class)).andReturn(requestParam);
        EasyMock.expect(requestParam.value()).andReturn(varName);
        EasyMock.expect(variableSubstitutionController.substitute(methodParameter, "var1", value)).andReturn(false);

        replayAll();

        requestParamAnnotationArgumentResolver.setTemplateVariables(uriTemplate, methodParameter, value, variableSubstitutionController);

        verifyAll();

    }
}