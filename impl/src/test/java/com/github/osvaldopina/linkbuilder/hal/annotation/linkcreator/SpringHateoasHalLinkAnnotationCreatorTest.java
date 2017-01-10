package com.github.osvaldopina.linkbuilder.hal.annotation.linkcreator;

import static org.easymock.EasyMock.expect;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.osvaldopina.linkbuilder.annotation.reader.properties.LinkAnnotationProperties;
import com.github.osvaldopina.linkbuilder.fromcall.MethodCall;
import com.github.osvaldopina.linkbuilder.hal.annotation.HalLinks;
import com.github.osvaldopina.linkbuilder.hal.annotation.linkcreator.embedded.valuesdiscover.EmbeddedValuesDiscover;
import com.github.osvaldopina.linkbuilder.hal.annotation.reader.HalLinkAnnotationReader;
import com.github.osvaldopina.linkbuilder.hal.annotation.reader.properties.HalLinkAnnotationProperties;
import com.github.osvaldopina.linkbuilder.hal.springhateoas.HalLink;
import com.github.osvaldopina.linkbuilder.urigeneration.base.BaseUriDiscover;
import com.github.osvaldopina.linkbuilder.urigeneration.link.annotation.AnnotationUriGenerator;
import com.github.osvaldopina.linkbuilder.urigeneration.link.methodcall.MethodCallUriGenerator;
import com.github.osvaldopina.linkbuilder.utils.IntrospectionUtils;
import org.easymock.EasyMockRule;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.hateoas.ResourceSupport;

@Ignore
public class SpringHateoasHalLinkAnnotationCreatorTest extends EasyMockSupport {

	@Rule
	public EasyMockRule mocks = new EasyMockRule(this);

	@Mock
	AnnotationUriGenerator annotationUriGenerator;

	@Mock
	IntrospectionUtils introspectionUtils;

	@Mock
	MethodCallUriGenerator methodCallUriGenerator;

	@Mock
	LinkAnnotationProperties linkAnnotationProperties;

	@Mock
	HalLinkAnnotationReader halLinkAnnotationReader;

	@Mock
	HalLinkAnnotationProperties halLinkAnnotationProperties;

	@Mock
	ObjectMapper objectMapper;

    @Mock
    EmbeddedValuesDiscover embeddedValuesDiscover;

	ResourceSupport resourceSupport = new ResourceSupport();

    ResourceSupport embeddedResource = new ResourceSupport();

	@Mock
	MethodCall methodCall;

	Method method = Object.class.getMethods()[0];

    @TestSubject
    SpringHateoasHalLinkAnnotationCreator springHateoasHalLinkAnnotationCreator = new
            SpringHateoasHalLinkAnnotationCreator(null, null, null , null, null);


	@Test
	public void canCreate_methodHasComposedHalLinksAnnotation() throws Exception {
		expect(introspectionUtils.hasComposedAnnotation(method, HalLinks.class)).andReturn(true);

		replayAll();
		assertThat(springHateoasHalLinkAnnotationCreator.canCreate(method), is(true));
		verifyAll();
	}

	@Test
	public void canCreate_methodHasNotComposedHalLinksAnnotation() throws Exception {
		expect(introspectionUtils.hasComposedAnnotation(method, HalLinks.class)).andReturn(false);

		replayAll();
		assertThat(springHateoasHalLinkAnnotationCreator.canCreate(method), is(false));
		verifyAll();
	}

	@Test
	public void canCreate_resourceHasComposedHalLinksAnnotation() throws Exception {
		expect(introspectionUtils.hasComposedAnnotation(resourceSupport.getClass(), HalLinks.class)).andReturn(true);

		replayAll();
		assertThat(springHateoasHalLinkAnnotationCreator.canCreate(resourceSupport), is(true));
		verifyAll();
	}

	@Test
	public void canCreate_resourceHasNotComposedHalLinksAnnotation() throws Exception {
		expect(introspectionUtils.hasComposedAnnotation(resourceSupport.getClass(), HalLinks.class)).andReturn(false);

		replayAll();
		assertThat(springHateoasHalLinkAnnotationCreator.canCreate(resourceSupport), is(false));
		verifyAll();
	}


	@Test
	public void createAndSetForMethodAnnotations_noEnableSelfFromCurrentCall() throws Exception {
		expect(methodCall.getMethod()).andReturn(method);
		expect(halLinkAnnotationReader.read(method)).andReturn(Arrays.asList((LinkAnnotationProperties) halLinkAnnotationProperties));
		expect(annotationUriGenerator.generateUri(halLinkAnnotationProperties, methodCall, resourceSupport)).andReturn("link-uri");
		expect(halLinkAnnotationProperties.getRel()).andReturn("rel");
		expect(halLinkAnnotationProperties.getHreflang()).andReturn("href-lang");
        expect(methodCall.getMethod()).andReturn(method);
        expect(introspectionUtils.isEnableSelfFromCurrentCallMethod(method)).andReturn(false);
		replayAll();

		springHateoasHalLinkAnnotationCreator.createAndSetForMethodAnnotations(methodCall, resourceSupport);

		verifyAll();

		assertThat(resourceSupport.getLinks(),  hasSize(1));
		HalLink halLink = (HalLink) resourceSupport.getLinks().get(0);
		assertThat(halLink.getHreflang(),is("href-lang"));
		assertThat(halLink.getRel(),is("rel"));
		assertThat(halLink.getHref(),is("link-uri"));

	}

    @Test
    public void createAndSetForMethodAnnotations_enableSelfFromCurrentCall() throws Exception {
        expect(methodCall.getMethod()).andReturn(method);
        expect(halLinkAnnotationReader.read(method)).andReturn(Arrays.asList((LinkAnnotationProperties) halLinkAnnotationProperties));
        expect(annotationUriGenerator.generateUri(halLinkAnnotationProperties, methodCall, resourceSupport)).andReturn("link-uri");
        expect(halLinkAnnotationProperties.getRel()).andReturn("rel");
        expect(halLinkAnnotationProperties.getHreflang()).andReturn("href-lang");
        expect(methodCall.getMethod()).andReturn(method);
        expect(introspectionUtils.isEnableSelfFromCurrentCallMethod(method)).andReturn(true);
        expect(methodCallUriGenerator.generateUri(methodCall, resourceSupport)).andReturn("self-uri");
        replayAll();

        springHateoasHalLinkAnnotationCreator.createAndSetForMethodAnnotations(methodCall, resourceSupport);

        verifyAll();

        assertThat(resourceSupport.getLinks(),  hasSize(2));
        assertThat(resourceSupport.getLink("rel"), is(notNullValue()));
        assertThat(resourceSupport.getLink("self"), is(notNullValue()));

        HalLink halLink = (HalLink) resourceSupport.getLink("rel");
        assertThat(halLink.getHreflang(),is("href-lang"));
        assertThat(halLink.getRel(),is("rel"));
        assertThat(halLink.getHref(),is("link-uri"));

        halLink = (HalLink) resourceSupport.getLink("self");
        assertThat(halLink.getHreflang(),is(nullValue()));
        assertThat(halLink.getRel(),is("self"));
        assertThat(halLink.getHref(),is("self-uri"));
    }

    @Test
    public void createAndSetForResourceAnnotations_noEmbeddedResource() throws Exception {
        expect(halLinkAnnotationReader.read(resourceSupport.getClass())).andReturn(Arrays.asList((LinkAnnotationProperties) halLinkAnnotationProperties));
        expect(annotationUriGenerator.generateUri(halLinkAnnotationProperties, methodCall, resourceSupport)).andReturn("link-uri");
        expect(halLinkAnnotationProperties.getRel()).andReturn("rel");
        expect(halLinkAnnotationProperties.getHreflang()).andReturn("href-lang");
        expect(embeddedValuesDiscover.getEmbeddedValues(objectMapper, resourceSupport)).andReturn(Collections.emptySet());

        replayAll();

        springHateoasHalLinkAnnotationCreator.createAndSetForResourceAnnotations(methodCall, resourceSupport);

        verifyAll();

        assertThat(resourceSupport.getLinks(),  hasSize(1));
        HalLink halLink = (HalLink) resourceSupport.getLinks().get(0);
        assertThat(halLink.getHreflang(),is("href-lang"));
        assertThat(halLink.getRel(),is("rel"));
        assertThat(halLink.getHref(),is("link-uri"));


    }

    @Test
    public void createAndSetForResourceAnnotations_embeddedResource() throws Exception {
        expect(halLinkAnnotationReader.read(resourceSupport.getClass())).andReturn(Arrays.asList((LinkAnnotationProperties) halLinkAnnotationProperties));
        expect(annotationUriGenerator.generateUri(halLinkAnnotationProperties, methodCall, resourceSupport)).andReturn("link-uri");
        expect(halLinkAnnotationProperties.getRel()).andReturn("rel");
        expect(halLinkAnnotationProperties.getHreflang()).andReturn("href-lang");
        expect(embeddedValuesDiscover.getEmbeddedValues(objectMapper, resourceSupport)).andReturn(new HashSet<Object>(Arrays.asList(embeddedResource)));

        expect(halLinkAnnotationReader.read(embeddedResource.getClass())).andReturn(Arrays.asList((LinkAnnotationProperties) halLinkAnnotationProperties));
        expect(annotationUriGenerator.generateUri(halLinkAnnotationProperties, methodCall, embeddedResource)).andReturn("other-link-uri");
        expect(halLinkAnnotationProperties.getRel()).andReturn("other-rel");
        expect(halLinkAnnotationProperties.getHreflang()).andReturn("other-href-lang");
        expect(embeddedValuesDiscover.getEmbeddedValues(objectMapper, embeddedResource)).andReturn(Collections.emptySet());

        replayAll();

        springHateoasHalLinkAnnotationCreator.createAndSetForResourceAnnotations(methodCall, resourceSupport);

        verifyAll();

        assertThat(resourceSupport.getLinks(),  hasSize(1));
        HalLink halLink = (HalLink) resourceSupport.getLinks().get(0);
        assertThat(halLink.getHreflang(),is("href-lang"));
        assertThat(halLink.getRel(),is("rel"));
        assertThat(halLink.getHref(),is("link-uri"));

        assertThat(embeddedResource.getLinks(),  hasSize(1));
        halLink = (HalLink) embeddedResource.getLinks().get(0);
        assertThat(halLink.getHreflang(),is("other-href-lang"));
        assertThat(halLink.getRel(),is("other-rel"));
        assertThat(halLink.getHref(),is("other-link-uri"));

    }
 }