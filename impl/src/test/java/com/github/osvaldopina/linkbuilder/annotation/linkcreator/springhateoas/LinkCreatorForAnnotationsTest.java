package com.github.osvaldopina.linkbuilder.annotation.linkcreator.springhateoas;

import com.github.osvaldopina.linkbuilder.LinkBuilderException;
import com.github.osvaldopina.linkbuilder.annotation.reader.properties.LinkAnnotationProperties;
import com.github.osvaldopina.linkbuilder.fromcall.MethodCall;
import com.github.osvaldopina.linkbuilder.urigeneration.link.annotation.AnnotationUriGenerator;
import com.github.osvaldopina.linkbuilder.urigeneration.link.methodcall.MethodCallUriGenerator;
import com.github.osvaldopina.linkbuilder.utils.IntrospectionUtils;
import org.easymock.EasyMockRule;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.core.io.Resource;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;

import java.lang.reflect.Method;

import static org.easymock.EasyMock.expect;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.Is.is;


public class LinkCreatorForAnnotationsTest extends EasyMockSupport {

    @Rule
    public EasyMockRule mocks = new EasyMockRule(this);


    @Mock
    private AnnotationUriGenerator annotationUriGenerator;

    @Mock
    private LinkAnnotationProperties linkAnnotationProperties;

    @Mock
    private MethodCall currentMethodCall;

    @Mock
    private MethodCallUriGenerator methodCallUriGenerator;

    @Mock
    private IntrospectionUtils introspectionUtils;

    private ResourceSupport resource;

    private Method method = Object.class.getMethod("toString");

    @TestSubject
    private LinkCreatorForAnnotations linkCreatorForAnnotations = new LinkCreatorForAnnotations();

    public LinkCreatorForAnnotationsTest() throws Exception {
    }

    @Before
    public void setUp() {
        resource = new ResourceSupport();

    }

    @Test(expected = LinkBuilderException.class)
    public void createAndSetForAnnotations_resourceIsNotResourceSupport() throws Exception {
        Object resourceNotResourceSupport = new Object();

        replayAll();

        linkCreatorForAnnotations.createAndSetForAnnotations(annotationUriGenerator, linkAnnotationProperties,
                currentMethodCall, resourceNotResourceSupport);

        verifyAll();
    }

    @Test
    public void createAndSetForAnnotations() throws Exception {
        expect(annotationUriGenerator.generateUri(linkAnnotationProperties, currentMethodCall, resource)).
                andReturn("link-uri");
        expect(linkAnnotationProperties.getRel()).andReturn("rel");

        replayAll();

        linkCreatorForAnnotations.createAndSetForAnnotations(annotationUriGenerator, linkAnnotationProperties,
                currentMethodCall, resource);

        verifyAll();

        assertThat(resource.getLinks(), hasSize(1));
        assertThat(resource.getLinks(), hasItem(new Link("link-uri", "rel")));

    }

    @Test
    public void createAndSetSelfLinkIfNeeded_doesNotHaveisEnableSelfFromCurrentCallMethodAnnotation() {
        expect(currentMethodCall.getMethod()).andReturn(method);
        expect(introspectionUtils.isEnableSelfFromCurrentCallMethod(method)).andReturn(true);
        expect(methodCallUriGenerator.generateUri(currentMethodCall, resource, false)).andReturn("self-uri");

        replayAll();

        linkCreatorForAnnotations.createAndSetSelfLinkIfNeeded(methodCallUriGenerator, introspectionUtils,
                currentMethodCall, resource);

        verifyAll();

        assertThat(resource.getLinks(), hasSize(1));
        assertThat(resource.getLinks(), hasItem(new Link("self-uri", "self")));
    }


}