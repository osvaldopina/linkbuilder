package com.github.osvaldopina.linkbuilder.urigeneration.link.methodcall.impl;

import com.github.osvaldopina.linkbuilder.fromcall.MethodCall;
import com.github.osvaldopina.linkbuilder.template.Template;
import com.github.osvaldopina.linkbuilder.template.TemplateRegistry;
import com.github.osvaldopina.linkbuilder.template.VariableValues;
import com.github.osvaldopina.linkbuilder.template.Variables;
import com.github.osvaldopina.linkbuilder.template.conditionalsubustitution.ConditionalVariableSubstitutionStrategies;
import com.github.osvaldopina.linkbuilder.template.variablevaluediscover.methodcall.MethodCallVariableValuesDiscover;
import com.github.osvaldopina.linkbuilder.urigeneration.base.BaseUriDiscover;
import com.github.osvaldopina.linkbuilder.utils.UrlPathContatenator;
import org.easymock.EasyMockRule;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Rule;
import org.junit.Test;

import java.lang.reflect.Method;

import static org.easymock.EasyMock.expect;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class MethodCallUriGeneratorImplTest extends EasyMockSupport {

    @Rule
    public EasyMockRule mocks = new EasyMockRule(this);

    @Mock
    private TemplateRegistry templateRegistry;

    @Mock
    private BaseUriDiscover baseUriDiscover;

    @Mock
    private MethodCallVariableValuesDiscover methodCallVariableValuesDiscover;

    @Mock
    private UrlPathContatenator urlPathContatenator;

    @Mock
    private DefaultConditionalVariableSubstitutionStategiesFactory defaultConditionalVariableSubstitutionStategiesFactory;

    @Mock
    private ConditionalVariableSubstitutionStrategies conditionalVariableSubstitutionStrategies;

    @Mock
    private Template template;

    @Mock
    private MethodCall methodCall;

    private Method method = Object.class.getMethod("toString");

    @Mock
    private VariableValues variableValues;

    @Mock
    private Variables variables;


    @Mock
    private Object resource;

    @TestSubject
    private MethodCallUriGeneratorImpl methodCallUriGeneratorImpl = new MethodCallUriGeneratorImpl(null, null, null);


    public MethodCallUriGeneratorImplTest() throws Exception {

    }

    @Test
    public void generateUri_noConditionalVariableSubstitutionStrategies() {
        expect(defaultConditionalVariableSubstitutionStategiesFactory.create()).andReturn(conditionalVariableSubstitutionStrategies);
        expect(methodCall.getMethod()).andReturn(method);
        expect(templateRegistry.getTemplate(method)).andReturn(template);
        expect(template.getVariables()).andReturn(variables);
        expect(methodCallVariableValuesDiscover.getVariableValues(variables, methodCall, resource,
                conditionalVariableSubstitutionStrategies)).andReturn(variableValues);
        expect(baseUriDiscover.getBaseUri()).andReturn("base-uri");
        expect(template.toUri(variableValues)).andReturn("template-uri");
        expect(urlPathContatenator.concat("base-uri", "template-uri")).andReturn("base-uri/template-uri");

        replayAll();

        assertThat(methodCallUriGeneratorImpl.generateUri(methodCall, resource), is("base-uri/template-uri"));

        verifyAll();

    }

    @Test
    public void generateUri_conditionalVariableSubstitutionStrategies() {
        expect(methodCall.getMethod()).andReturn(method);
        expect(templateRegistry.getTemplate(method)).andReturn(template);
        expect(template.getVariables()).andReturn(variables);
        expect(methodCallVariableValuesDiscover.getVariableValues(variables, methodCall, resource,
                conditionalVariableSubstitutionStrategies)).andReturn(variableValues);
        expect(baseUriDiscover.getBaseUri()).andReturn("base-uri");
        expect(template.toUri(variableValues)).andReturn("template-uri");
        expect(urlPathContatenator.concat("base-uri", "template-uri")).andReturn("base-uri/template-uri");

        replayAll();

        assertThat(methodCallUriGeneratorImpl.generateUri(methodCall, resource,conditionalVariableSubstitutionStrategies),
                is("base-uri/template-uri"));

        verifyAll();

    }

}