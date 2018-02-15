package com.github.osvaldopina.linkbuilder.argumentresolver.basic;

import com.damnhandy.uri.template.UriTemplate;
import com.damnhandy.uri.template.UriTemplateBuilder;
import com.github.osvaldopina.linkbuilder.LinkBuilderException;
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

public class QueryVariableAnnotationArgumentResolverTest extends EasyMockSupport {

    @Rule
    public EasyMockRule mocks = new EasyMockRule(this);

    @Mock
    IntrospectionUtils introspectionUtils;

    @TestSubject
    private QueryVariableAnnotationArgumentResolver queryVariableAnnotationArgumentResolver =
            new QueryVariableAnnotationArgumentResolver(introspectionUtils);

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

        method = QueryVariableAnnotationArgumentResolverTest.class.getMethod("equals", Object.class);
    }


    @Test
    public void resolveForAnnotatedMethod() throws Exception {

        EasyMock.expect(introspectionUtils.isQueryVariableParameter(method, 0)).andReturn(true);

        replayAll();

        assertTrue(queryVariableAnnotationArgumentResolver.resolveFor(method, 0));

        verifyAll();

    }

    @Test
    public void resolveForNonAnnotatedMethod() throws Exception {

        EasyMock.expect(introspectionUtils.isQueryVariableParameter(method, 0)).andReturn(false);

        replayAll();

        assertFalse(queryVariableAnnotationArgumentResolver.resolveFor(method, 0));

        verifyAll();

    }

    @Test
    public void augmentTemplate() throws Exception {
        String varName = "var1";

        EasyMock.expect(introspectionUtils.getQueryVariableName(method, 0)).andReturn(varName);
        uriTemplateAugmenter.addToQuery(varName);
        EasyMock.expectLastCall();

        replayAll();

        queryVariableAnnotationArgumentResolver.augmentTemplate(uriTemplateAugmenter, method, 0);

        verifyAll();
    }

    @Test(expected = LinkBuilderException.class)
    public void setTemplateVariablesParamNameNotInTemplatedParamNames() throws Exception {
        String varName = "var1";
        String value = "value-for-var1";

        EasyMock.expect(introspectionUtils.getQueryVariableName(method, 0)).andReturn(varName);
        EasyMock.expect(variableSubstitutionController.substitute(method, 0, varName, value)).andReturn(true);
        EasyMock.expect(uriTemplate.getVariables()).andReturn(new String[] {"var2"});
        EasyMock.expect(uriTemplate.getTemplate()).andReturn("template");

        replayAll();

        queryVariableAnnotationArgumentResolver.setTemplateVariables(uriTemplate, method, 0, value, variableSubstitutionController);

        verifyAll();

    }

    @Test
    public void setTemplateVariablesParamNameInTemplatedParamNames() throws Exception {
        String varName = "var1";
        String value = "value-for-var1";

        EasyMock.expect(introspectionUtils.getQueryVariableName(method, 0)).andReturn(varName);
        EasyMock.expect(variableSubstitutionController.substitute(method, 0, varName, value)).andReturn(true);
        EasyMock.expect(uriTemplate.getVariables()).andReturn(new String[] {"var1"});
        EasyMock.expect(uriTemplate.set(varName, value)).andReturn(uriTemplate);


        replayAll();

        queryVariableAnnotationArgumentResolver.setTemplateVariables(uriTemplate, method, 0, value, variableSubstitutionController);

        verifyAll();

    }

    @Test
    public void setTemplateVariablesSubstitutionControllerSubstitutionNotAllowed() throws Exception {
        String varName = "var1";
        String value = "value-for-var1";

        EasyMock.expect(introspectionUtils.getQueryVariableName(method, 0)).andReturn(varName);
        EasyMock.expect(variableSubstitutionController.substitute(method, 0, varName, value)).andReturn(false);

        replayAll();

        queryVariableAnnotationArgumentResolver.setTemplateVariables(uriTemplate, method, 0, value, variableSubstitutionController);

        verifyAll();

    }

}