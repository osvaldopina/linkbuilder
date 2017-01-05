package com.github.osvaldopina.linkbuilder.hal.annotation.linkcreator;

import static org.easymock.EasyMock.expect;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.lang.reflect.Method;

import com.github.osvaldopina.linkbuilder.annotation.reader.properties.LinkAnnotationProperties;
import com.github.osvaldopina.linkbuilder.fromcall.MethodCall;
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
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.hateoas.ResourceSupport;

@Ignore
public class SpringHateoasHalLinkAnnotationCreatorTest extends EasyMockSupport {

	@Rule
	public EasyMockRule mocks = new EasyMockRule(this);


	@Mock
	BaseUriDiscover baseUriDiscover;

	@Mock
	AnnotationUriGenerator annotationUriGenerator;

	@Mock
	IntrospectionUtils introspectionUtils;

	@Mock
	MethodCallUriGenerator methodCallUriGenerator;

	@Mock
	LinkAnnotationProperties linkAnnotationProperties;

	@Mock
	HalLinkAnnotationProperties halLinkAnnotationProperties;

	ResourceSupport resourceSupport = new ResourceSupport();

	@Mock
	MethodCall methodCall;

	Method method = Object.class.getMethods()[0];

	Object payloadNotResourceSupport = new Object();

//	@TestSubject
//	SpringHateoasHalLinkAnnotationCreator springHateoasHalLinkAnnotationCreator =
//			new SpringHateoasHalLinkAnnotationCreator(baseUriDiscover,
//					annotationUriGenerator, introspectionUtils, methodCallUriGenerator);

	@Test
	public void canCreate_linkAnnotationIsHalLinkAnnotationAndPayloadIsResourceSupport() throws Exception {

		replayAll();
//		assertThat(springHateoasHalLinkAnnotationCreator.canCreate(halLinkAnnotationProperties, resourceSupport), is(true));
		verifyAll();
	}

	@Test
	public void canCreate_linkAnnotationIsHalLinkAnnotationAndPayloadIsNotResourceSupport() throws Exception {

		replayAll();
//		assertThat(springHateoasHalLinkAnnotationCreator.canCreate(halLinkAnnotationProperties, payloadNotResourceSupport), is(false));
		verifyAll();
	}

	@Test
	public void canCreate_linkAnnotationIsNotHalLinkAnnotationAndPayloadIsNotResourceSupport() throws Exception {

		replayAll();
//		assertThat(springHateoasHalLinkAnnotationCreator.canCreate(linkAnnotationProperties, payloadNotResourceSupport), is(false));
		verifyAll();
	}

	@Test
	public void canCreate_linkAnnotationIsNotHalLinkAnnotationAndPayloadIsResourceSupport() throws Exception {

		replayAll();
//		assertThat(springHateoasHalLinkAnnotationCreator.canCreate(linkAnnotationProperties, resourceSupport), is(false));
		verifyAll();
	}

	@Test
	public void canCreateMethodCall() throws Exception {

		replayAll();
//		assertThat(springHateoasHalLinkAnnotationCreator.canCreate(methodCall, resourceSupport), is(true));
		verifyAll();
	}

	@Test
	public void canCreateMethodCall_notResourceSupport() throws Exception {

		replayAll();
//		assertThat(springHateoasHalLinkAnnotationCreator.canCreate(methodCall, payloadNotResourceSupport), is(false));
		verifyAll();
	}


	@Test
	public void createAndSet() throws Exception {
		expect(baseUriDiscover.getBaseUri()).andReturn("base-uri");
		expect(annotationUriGenerator.generateUri(halLinkAnnotationProperties, methodCall, resourceSupport)).andReturn("link-uri");
		expect(halLinkAnnotationProperties.getRel()).andReturn("rel");
		expect(halLinkAnnotationProperties.getHreflang()).andReturn("href-lang");

		replayAll();

//		springHateoasHalLinkAnnotationCreator.createAndSetForMethodAnnotations(halLinkAnnotationProperties, methodCall, resourceSupport);

		verifyAll();

		assertThat(resourceSupport.getLinks(),  hasSize(1));
		HalLink halLink = (HalLink) resourceSupport.getLinks().get(0);
		assertThat(halLink.getHreflang(),is("href-lang"));
		assertThat(halLink.getRel(),is("rel"));
		assertThat(halLink.getHref(),is("base-uri/link-uri"));

	}

	@Test
	public void createAndSet_notResourceSupport() throws Exception {

		replayAll();

//		springHateoasHalLinkAnnotationCreator.createAndSetForMethodAnnotations(halLinkAnnotationProperties, methodCall, payloadNotResourceSupport);
		assertThat(resourceSupport.getLinks(), hasSize(0));

		verifyAll();
	}

	@Test
	public void createAndSetSelfLinkIfNeeded_isNotEnableSelfFromCurrentCallMethod() throws Exception {

		expect(methodCall.getMethod()).andReturn(method);
		expect(introspectionUtils.isEnableSelfFromCurrentCallMethod(method)).andReturn(false);

		replayAll();
//		springHateoasHalLinkAnnotationCreator.createAndSetSelfLinkIfNeeded(methodCall, resourceSupport);
		verifyAll();

		assertThat(resourceSupport.getLinks(),  hasSize(0));

	}

	@Test
	public void createAndSetSelfLinkIfNeeded() throws Exception {

		expect(methodCall.getMethod()).andReturn(method);
		expect(introspectionUtils.isEnableSelfFromCurrentCallMethod(method)).andReturn(true);
		expect(methodCallUriGenerator.generateUri(methodCall, resourceSupport)).andReturn("uri");

		replayAll();
//		springHateoasHalLinkAnnotationCreator.createAndSetSelfLinkIfNeeded(methodCall, resourceSupport);
		verifyAll();

		assertThat(resourceSupport.getLinks(),  hasSize(1));
		HalLink halLink = (HalLink) resourceSupport.getLinks().get(0);
		assertThat(halLink.getHreflang(),is(nullValue()));
		assertThat(halLink.getRel(),is("self"));
		assertThat(halLink.getHref(),is("uri"));

	}

}