package com.github.osvaldopina.linkbuilder.argumentresolver.basic;

import com.damnhandy.uri.template.UriTemplate;
import com.damnhandy.uri.template.UriTemplateBuilder;
import com.github.osvaldopina.linkbuilder.argumentresolver.basic.PathVariableAnnotationArgumentResolver;
import com.github.osvaldopina.linkbuilder.argumentresolver.variablesubstitutioncontroller.VariableSubstitutionController;
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
    private VariableSubstitutionController variableSubstitutionController;


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
        EasyMock.expect(variableSubstitutionController.substitute(methodParameter, "var1", value)).andReturn(true);
        EasyMock.expect(methodParameter.getMethodAnnotation(PathVariable.class)).andReturn(pathVariable);
        EasyMock.expect(pathVariable.value()).andReturn(varName);
        EasyMock.expect(uriTemplate.set(varName, value)).andReturn(uriTemplate);

        replayAll();

        pathVariableAnnotationArgumentResolver.setTemplateVariables(uriTemplate, methodParameter, value, variableSubstitutionController);

        verifyAll();

    }

    @Test
    public void setTemplateVariablesParamNameInTemplatedParamNames() throws Exception {
        String varName = "var1";
        String value = "value-for-var1";

        EasyMock.expect(methodParameter.getMethodAnnotation(PathVariable.class)).andReturn(pathVariable);
        EasyMock.expect(pathVariable.value()).andReturn(varName);
        EasyMock.expect(variableSubstitutionController.substitute(methodParameter, "var1", value)).andReturn(false);

        replayAll();

        pathVariableAnnotationArgumentResolver.setTemplateVariables(uriTemplate, methodParameter, value, variableSubstitutionController);

        verifyAll();

    }
}