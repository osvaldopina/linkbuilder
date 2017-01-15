package com.github.osvaldopina.linkbuilder.annotation.linkcreator.springhateoas;

import static org.easymock.EasyMock.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.lang.reflect.Method;
import java.util.Arrays;

import com.github.osvaldopina.linkbuilder.LinkBuilderException;
import com.github.osvaldopina.linkbuilder.annotation.reader.properties.LinkAnnotationProperties;
import com.github.osvaldopina.linkbuilder.fromcall.MethodCall;
import com.github.osvaldopina.linkbuilder.annotation.linkcreator.LinkAnnotationCreator;
import org.easymock.EasyMockRule;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;

public class LinkAnnotationCreatorRegistryImplTest extends EasyMockSupport {

	@Rule
	public EasyMockRule mocks = new EasyMockRule(this);

	private LinkAnnotationCreatorRegistryImpl linkAnnotationCreatorRegistryImpl;

	@Mock
	private LinkAnnotationCreator linkAnnotationCreator;

	@Mock
	private Object resource;

	private Method method = Object.class.getMethod("toString");

	@Before
	public void setUp() {
		linkAnnotationCreatorRegistryImpl = new LinkAnnotationCreatorRegistryImpl(Arrays.asList(linkAnnotationCreator));
	}


	public LinkAnnotationCreatorRegistryImplTest() throws Exception {

	}

	@Test
	public void get_methodLinkAnnotationCreatorCanCreate() {
		expect(linkAnnotationCreator.canCreate(method)).andReturn(true);

		replayAll();

		assertThat(linkAnnotationCreatorRegistryImpl.get(method), is(sameInstance(linkAnnotationCreator)));

		verifyAll();
	}

	@Test(expected = LinkBuilderException.class)
	public void get_methodLinkAnnotationCreatorCannotCreate() {
		expect(linkAnnotationCreator.canCreate(method)).andReturn(false);

		replayAll();

		linkAnnotationCreatorRegistryImpl.get(method);

	}

	@Test
	public void get_resourceLinkAnnotationCreatorCanCreate() {
		expect(linkAnnotationCreator.canCreate(resource)).andReturn(true);

		replayAll();

		assertThat(linkAnnotationCreatorRegistryImpl.get(resource), is(sameInstance(linkAnnotationCreator)));

		verifyAll();
	}

	@Test(expected = LinkBuilderException.class)
	public void get_resourceLinkAnnotationCreatorCannotCreate() {
		expect(linkAnnotationCreator.canCreate(resource)).andReturn(false);

		replayAll();

		linkAnnotationCreatorRegistryImpl.get(resource);

	}
}