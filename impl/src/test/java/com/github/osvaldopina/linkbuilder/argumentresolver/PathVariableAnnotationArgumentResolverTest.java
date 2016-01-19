package com.github.osvaldopina.linkbuilder.argumentresolver;

import com.damnhandy.uri.template.UriTemplate;
import com.damnhandy.uri.template.UriTemplateBuilder;
import com.github.osvaldopina.linkbuilder.utils.UriTemplateAugmenter;
import org.easymock.*;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by osvaldopina on 1/4/16.
 */
public class PathVariableAnnotationArgumentResolverTest extends EasyMockSupport {

    @Rule
    public EasyMockRule mocks = new EasyMockRule(this);

    @TestSubject
    private PathVariableAnnotationArgumentResolver pathVariableAnnotationArgumentResolver = new PathVariableAnnotationArgumentResolver();

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
    private PathVariable pathVariable;

    @Mock
    private List<String> templatedParamNames;


    @Before
    public void setUp() {

        uriTemplateBuilder = UriTemplate.createBuilder();
    }


    @Test
    public void resolveForAnnotatedMethod() throws Exception {

        EasyMock.expect(methodParameter.hasParameterAnnotation(PathVariable.class)).andReturn(true);

        replayAll();

        assertTrue(pathVariableAnnotationArgumentResolver.resolveFor(methodParameter));

        verifyAll();

    }

    @Test
    public void resolveForNonAnnotatedMethod() throws Exception {

        EasyMock.expect(methodParameter.hasParameterAnnotation(PathVariable.class)).andReturn(false);

        replayAll();

        assertFalse(pathVariableAnnotationArgumentResolver.resolveFor(methodParameter));

        verifyAll();

    }

    @Test
    public void augmentTemplate() throws Exception {
        String varName = "var1";

        replayAll();

        pathVariableAnnotationArgumentResolver.augmentTemplate(uriTemplateAugmenter, methodParameter);

        verifyAll();



    }

    @Test
    public void setTemplateVariablesParamNameNotInTemplatedParamNames() throws Exception {
        String varName = "var1";
        String value = "value-for-var1";

        EasyMock.expect(pathVariable.value()).andReturn(varName);
        EasyMock.expect(templatedParamNames.contains("var1")).andReturn(false);
        EasyMock.expect(methodParameter.getParameterAnnotation(PathVariable.class)).andReturn(pathVariable);
        EasyMock.expect(pathVariable.value()).andReturn(varName);
        EasyMock.expect(uriTemplate.set(varName, value)).andReturn(uriTemplate);

        replayAll();

        pathVariableAnnotationArgumentResolver.setTemplateVariables(uriTemplate, methodParameter, value, templatedParamNames);

        verifyAll();

    }

    @Test
    public void setTemplateVariablesParamNameInTemplatedParamNames() throws Exception {
        String varName = "var1";
        String value = "value-for-var1";

        EasyMock.expect(methodParameter.getParameterAnnotation(PathVariable.class)).andReturn(pathVariable);
        EasyMock.expect(pathVariable.value()).andReturn(varName);
        EasyMock.expect(templatedParamNames.contains("var1")).andReturn(true);

        replayAll();

        pathVariableAnnotationArgumentResolver.setTemplateVariables(uriTemplate, methodParameter, value, templatedParamNames);

        verifyAll();

    }
}