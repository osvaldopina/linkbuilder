package com.github.osvaldopina.linkbuilder.linkcreator.annotation.springhateoas;

import static org.easymock.EasyMock.expect;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.lang.reflect.Method;
import java.util.Collections;

import com.github.osvaldopina.linkbuilder.LinkBuilderException;
import com.github.osvaldopina.linkbuilder.annotation.reader.properties.LinkAnnotationProperties;
import com.github.osvaldopina.linkbuilder.fromcall.MethodCall;
import com.github.osvaldopina.linkbuilder.urigeneration.base.BaseUriDiscover;
import com.github.osvaldopina.linkbuilder.urigeneration.link.annotation.AnnotationUriGenerator;
import com.github.osvaldopina.linkbuilder.urigeneration.link.methodcall.MethodCallUriGenerator;
import com.github.osvaldopina.linkbuilder.utils.IntrospectionUtils;
import org.easymock.EasyMockRule;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;

public class SpringHateoasLinkAnnotationCreatorTest extends EasyMockSupport {

	@Rule
	public EasyMockRule mocks = new EasyMockRule(this);

	@Mock
	private BaseUriDiscover baseUriDiscover;

	@Mock
	private AnnotationUriGenerator annotationUriGenerator;

	@Mock
	private IntrospectionUtils introspectionUtils;

	@Mock
	private MethodCallUriGenerator methodCallUriGenerator;

	private LinkAnnotationProperties linkAnnotationProperties =
			new LinkAnnotationProperties(null,null, false, Collections.EMPTY_LIST);

	@Mock
	private LinkAnnotationProperties linkAnnotationPropertiesMock;

	ResourceSupport payloadAsResourceSupport;

	@Mock
	Object payloadAsObject;

	@Mock
	MethodCall methodCall;

	Method method = Object.class.getMethods()[0];

	private SpringHateoasLinkAnnotationCreator springHateoasLinkAnnotationCreator;


	@Before
	public void setUp() {
		springHateoasLinkAnnotationCreator =
				new SpringHateoasLinkAnnotationCreator(baseUriDiscover,
						annotationUriGenerator, introspectionUtils, methodCallUriGenerator);

		payloadAsResourceSupport = new ResourceSupport();
	}

	@Test
	public void canCreateLinkAnnotationPropertiesPayload_linkPropertiesIsLinkAnnotationPropertiesClassAndPayloadIsResourceSupport() {

		replayAll();

		assertThat(springHateoasLinkAnnotationCreator.canCreate(linkAnnotationProperties, payloadAsResourceSupport), is(true));

		verifyAll();
	}

	@Test
	public void canCreateLinkAnnotationPropertiesPayload_linkPropertiesIsNotLinkAnnotationPropertiesClassAndPayloadIsResourceSupport() {

		replayAll();

		assertThat(springHateoasLinkAnnotationCreator.canCreate(linkAnnotationPropertiesMock, payloadAsResourceSupport), is(false));

		verifyAll();
	}

	@Test
	public void canCreateLinkAnnotationPropertiesPayload_linkPropertiesIsLinkAnnotationPropertiesClassAndPayloadIsNotResourceSupport() {

		replayAll();

		assertThat(springHateoasLinkAnnotationCreator.canCreate(linkAnnotationProperties, payloadAsObject), is(false));

		verifyAll();
	}

	@Test
	public void canCreateMethodCallPayLoad() {

		replayAll();

		assertThat(springHateoasLinkAnnotationCreator.canCreate(methodCall, payloadAsResourceSupport), is(true));

		verifyAll();
	}

	@Test
	public void canCreateMethodCallPayLoad_payloadIsNull() {

		replayAll();

		assertThat(springHateoasLinkAnnotationCreator.canCreate(methodCall, null), is(false));

		verifyAll();
	}

	@Test
	public void canCreateMethodCallPayLoad_isNotResourceSupport() {

		replayAll();

		assertThat(springHateoasLinkAnnotationCreator.canCreate(methodCall, payloadAsObject), is(false));

		verifyAll();
	}

	@Test(expected = LinkBuilderException.class)
	public void createAndSet_payloadIsNotRessourceSupport() {

		replayAll();

		springHateoasLinkAnnotationCreator.createAndSet(linkAnnotationPropertiesMock, methodCall, payloadAsObject);

		verifyAll();

	}

	@Test
	public void createAndSet() {
		expect(baseUriDiscover.getBaseUri()).andReturn("base-uri");
		expect(annotationUriGenerator.generateUri(linkAnnotationPropertiesMock, methodCall, payloadAsResourceSupport)).andReturn("method-uri");
		expect(linkAnnotationPropertiesMock.getRel()).andReturn("rel");

		replayAll();

		springHateoasLinkAnnotationCreator.createAndSet(linkAnnotationPropertiesMock, methodCall, payloadAsResourceSupport);

		verifyAll();

		assertThat(payloadAsResourceSupport.getLinks(), hasSize(1));
		Link link = payloadAsResourceSupport.getLinks().get(0);
		assertThat(link.getHref(), is("base-uri/method-uri"));
		assertThat(link.getRel(), is("rel"));


	}

	@Test
	public void createAndSetSelfLinkIfNeeded() {

		expect(methodCall.getMethod()).andReturn(method);
		expect(introspectionUtils.isEnableSelfFromCurrentCallMethod(method)).andReturn(true);
		expect(methodCallUriGenerator.generateUri(methodCall, payloadAsResourceSupport)).andReturn("method-uri");

		replayAll();

		springHateoasLinkAnnotationCreator.createAndSetSelfLinkIfNeeded(methodCall, payloadAsResourceSupport);

		verifyAll();

		assertThat(payloadAsResourceSupport.getLinks(), hasSize(1));
		Link link = payloadAsResourceSupport.getLinks().get(0);
		assertThat(link.getHref(), is("method-uri"));
		assertThat(link.getRel(), is("self"));

	}

	@Test
	public void createAndSetSelfLinkIfNeeded_IsNotAnnotatedWithEnableSelfFromCurrentCall() {

		expect(methodCall.getMethod()).andReturn(method);
		expect(introspectionUtils.isEnableSelfFromCurrentCallMethod(method)).andReturn(false);

		replayAll();

		springHateoasLinkAnnotationCreator.createAndSetSelfLinkIfNeeded(methodCall, payloadAsResourceSupport);

		verifyAll();

		assertThat(payloadAsResourceSupport.getLinks(), hasSize(0));

	}

	@Test(expected = LinkBuilderException.class)
	public void createAndSetSelfLinkIfNeeded_IsAnnotatedWithEnableSelfFromCurrentCallButPayloadIsNotResourceSupport() {

		expect(methodCall.getMethod()).andReturn(method);
		expect(introspectionUtils.isEnableSelfFromCurrentCallMethod(method)).andReturn(true);

		replayAll();

		springHateoasLinkAnnotationCreator.createAndSetSelfLinkIfNeeded(methodCall, payloadAsObject);

	}

}