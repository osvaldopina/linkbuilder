package com.github.osvaldopina.linkbuilder.urigeneration.link.annotation.impl;

import com.github.osvaldopina.linkbuilder.annotation.reader.properties.LinkAnnotationProperties;
import com.github.osvaldopina.linkbuilder.fromcall.MethodCall;
import com.github.osvaldopina.linkbuilder.linkdestination.LinkDestinationRegistry;
import com.github.osvaldopina.linkbuilder.template.Template;
import com.github.osvaldopina.linkbuilder.template.TemplateRegistry;
import com.github.osvaldopina.linkbuilder.template.VariableValues;
import com.github.osvaldopina.linkbuilder.template.Variables;
import com.github.osvaldopina.linkbuilder.template.variablevaluediscover.annotation.AnnotationVariableValuesDiscover;
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


public class AnnotationUriGeneratorImplTest extends EasyMockSupport {

    @Rule
    public EasyMockRule mocks = new EasyMockRule(this);

    @Mock
    private LinkDestinationRegistry linkDestinationRegistry;

    @Mock
    private TemplateRegistry templateRegistry;

    @Mock
    private AnnotationVariableValuesDiscover annotationVariableValuesDiscover;

    @Mock
    private BaseUriDiscover baseUriDiscover;

    @Mock
    private UrlPathContatenator urlPathContatenator;

    @Mock
    private Template template;

    @Mock
    LinkAnnotationProperties linkAnnotationProperties;

    @Mock
    Variables variables;

    @Mock
    MethodCall methodCall;

    @Mock
    Object resource;

    @Mock
    VariableValues variableValues;

    private Method method = Object.class.getMethod("toString");

    @TestSubject
    private AnnotationUriGeneratorImpl annotationUriGeneratorImpl = new AnnotationUriGeneratorImpl(null, null, null, null);

    public AnnotationUriGeneratorImplTest() throws Exception {

    }

    @Test
    public void generateUri_notTemplated() throws Exception {
        expect(linkAnnotationProperties.getDestination()).andReturn("destination");
        expect(linkDestinationRegistry.getTemplatedMethod("destination")).andReturn(method);
        expect(templateRegistry.getTemplate(method)).andReturn(template);
        expect(template.getVariables()).andReturn(variables);
        expect(annotationVariableValuesDiscover.getVariableValues(variables, methodCall, resource,
                linkAnnotationProperties)).andReturn(variableValues);
        expect(linkAnnotationProperties.isTemplated()).andReturn(false);
        expect(baseUriDiscover.getBaseUri()).andReturn("base-uri");
        expect(template.toUri(variableValues)).andReturn("templated-uri");
        expect(urlPathContatenator.concat("base-uri", "templated-uri")).andReturn("base-uri/templated-uri");
        replayAll();

        assertThat(annotationUriGeneratorImpl.generateUri(linkAnnotationProperties, methodCall, resource),
                is("base-uri/templated-uri"));

        verifyAll();


    }

    @Test
    public void generateUri_templated() throws Exception {
        expect(linkAnnotationProperties.getDestination()).andReturn("destination");
        expect(linkDestinationRegistry.getTemplatedMethod("destination")).andReturn(method);
        expect(templateRegistry.getTemplate(method)).andReturn(template);
        expect(template.getVariables()).andReturn(variables);
        expect(annotationVariableValuesDiscover.getVariableValues(variables, methodCall, resource,
                linkAnnotationProperties)).andReturn(variableValues);
        expect(linkAnnotationProperties.isTemplated()).andReturn(true);
        expect(baseUriDiscover.getBaseUri()).andReturn("base-uri");
        expect(template.toTemplatedUri(variableValues)).andReturn("templated-uri");
        expect(urlPathContatenator.concat("base-uri", "templated-uri")).andReturn("base-uri/templated-uri");
        replayAll();

        assertThat(annotationUriGeneratorImpl.generateUri(linkAnnotationProperties, methodCall, resource),
                is("base-uri/templated-uri"));

        verifyAll();


    }

}