package com.github.osvaldopina.linkbuilder.annotation.linkcreator.springhateoas;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.github.osvaldopina.linkbuilder.LinkBuilderException;
import com.github.osvaldopina.linkbuilder.annotation.Links;
import com.github.osvaldopina.linkbuilder.annotation.reader.impl.LinkAnnotationReader;
import com.github.osvaldopina.linkbuilder.annotation.reader.properties.LinkAnnotationProperties;
import com.github.osvaldopina.linkbuilder.fromcall.MethodCall;
import com.github.osvaldopina.linkbuilder.hal.annotation.HalLinks;
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
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;

public class SpringHateoasLinkAnnotationCreatorTest extends EasyMockSupport {

	@Rule
	public EasyMockRule mocks = new EasyMockRule(this);


	@Mock
	private AnnotationUriGenerator annotationUriGenerator;

	@Mock
	private IntrospectionUtils introspectionUtils;

	@Mock
	private MethodCallUriGenerator methodCallUriGenerator;

	@Mock
	private LinkAnnotationReader linkAnnotationReader;

	@Mock
	private LinkCreatorForAnnotations linkCreatorForAnnotations;

	@Mock
	private LinkAnnotationProperties linkAnnotationProperties;

	private List<LinkAnnotationProperties> linkAnnotationPropertiesList;

	ResourceSupport resource = new ResourceSupport();

	@Mock
	MethodCall methodCall;

	Method method = Object.class.getMethods()[0];

	@TestSubject
	private SpringHateoasLinkAnnotationCreator springHateoasLinkAnnotationCreator =
			new SpringHateoasLinkAnnotationCreator(null, null, null, null, null);


	@Before
	public void setUp() {
		linkAnnotationPropertiesList = new ArrayList<LinkAnnotationProperties>();
		linkAnnotationPropertiesList.add(linkAnnotationProperties);
	}



	@Test
	public void canCreate_methodIsAnnotatedWithLinks() {
        expect(introspectionUtils.hasComposedAnnotation(method, Links.class)).andReturn(true);

		replayAll();

		assertThat(springHateoasLinkAnnotationCreator.canCreate(method), is(true));

		verifyAll();
	}

	@Test
	public void canCreate_methodIsNotAnnotatedWithHalLinls() {
		expect(introspectionUtils.hasComposedAnnotation(method, Links.class)).andReturn(false);

		replayAll();

		assertThat(springHateoasLinkAnnotationCreator.canCreate(method), is(false));

		verifyAll();
	}

	@Test
	public void createAndSetForMethodAnnotations() {
		expect(methodCall.getMethod()).andReturn(method);
		expect(linkAnnotationReader.read(method)).andReturn(linkAnnotationPropertiesList);
		linkCreatorForAnnotations.createAndSetForAnnotations(annotationUriGenerator, linkAnnotationProperties,
				methodCall, resource);
		expectLastCall();

		replayAll();

		springHateoasLinkAnnotationCreator.createAndSetForMethodAnnotations(methodCall, resource);

		verifyAll();
	}

	@Test
	public void canCreateLinkAnnotationPropertiesResource_linkPropertiesIsLinkAnnotationPropertiesClassAndResourceIsNotResourceSupport() {
		expect(methodCall.getMethod()).andReturn(method);
		expect(linkAnnotationReader.read(method)).andReturn(linkAnnotationPropertiesList);
		linkCreatorForAnnotations.createAndSetForAnnotations(annotationUriGenerator, linkAnnotationProperties,
				methodCall, resource);
		expectLastCall();

		replayAll();

		springHateoasLinkAnnotationCreator.createAndSetForMethodAnnotations(methodCall, resource);

		verifyAll();
	}

	@Test
	public void canCreate_resourceIsNull() {

		replayAll();

		assertThat(springHateoasLinkAnnotationCreator.canCreate((Object) null), is(false));

		verifyAll();
	}

	@Test
	public void canCreate_resourceIsNotResourceSupport() {

		replayAll();

		assertThat(springHateoasLinkAnnotationCreator.canCreate(new Object()), is(false));

		verifyAll();
	}

	@Test
	public void canCreate_resourceIsResourceSupportButHasNoLinksAnnotation() {
		expect(introspectionUtils.hasComposedAnnotation(resource.getClass(), Links.class)).andReturn(false);

		replayAll();

		assertThat(springHateoasLinkAnnotationCreator.canCreate(resource), is(false));

		verifyAll();
	}

	@Test
	public void canCreate_resourceIsResourceSupportHasNoLinksAnnotation() {
		expect(introspectionUtils.hasComposedAnnotation(resource.getClass(), Links.class)).andReturn(true);

		replayAll();

		assertThat(springHateoasLinkAnnotationCreator.canCreate(resource), is(true));

		verifyAll();
	}

	@Test
	public void createAndSetForResourceAnnotations() {
		expect(linkAnnotationReader.read(resource.getClass())).andReturn(linkAnnotationPropertiesList);
		linkCreatorForAnnotations.createAndSetForAnnotations(annotationUriGenerator, linkAnnotationProperties,
				methodCall, resource);
		expectLastCall();

		replayAll();

		springHateoasLinkAnnotationCreator.createAndSetForResourceAnnotations(methodCall, resource);

		verifyAll();
	}

}