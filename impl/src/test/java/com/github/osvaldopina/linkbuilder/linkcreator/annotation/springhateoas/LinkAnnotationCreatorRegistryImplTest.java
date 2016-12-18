package com.github.osvaldopina.linkbuilder.linkcreator.annotation.springhateoas;

import static org.easymock.EasyMock.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.util.Arrays;

import com.github.osvaldopina.linkbuilder.LinkBuilderException;
import com.github.osvaldopina.linkbuilder.annotation.reader.properties.LinkAnnotationProperties;
import com.github.osvaldopina.linkbuilder.fromcall.MethodCall;
import com.github.osvaldopina.linkbuilder.linkcreator.annotation.LinkAnnotationCreator;
import org.easymock.EasyMockRule;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class LinkAnnotationCreatorRegistryImplTest extends EasyMockSupport {

	@Rule
	public EasyMockRule mocks = new EasyMockRule(this);

	private LinkAnnotationCreatorRegistryImpl linkAnnotationCreatorRegistryImpl;

	@Mock
	private LinkAnnotationCreator linkAnnotationCreator;

	@Mock
	private LinkAnnotationProperties linkAnnotationProperties;

	@Mock
	private Object payload;

	@Mock
	private MethodCall methodCall;

	@Before
	public void setUp() {
		linkAnnotationCreatorRegistryImpl = new LinkAnnotationCreatorRegistryImpl(Arrays.asList(linkAnnotationCreator));
	}


	@Test
	public void get_linkAnnotationPropertiesPayloadCanCreateTrue() {
		expect(linkAnnotationCreator.canCreate(linkAnnotationProperties, payload)).andReturn(true);

		replayAll();

		assertThat(linkAnnotationCreatorRegistryImpl.get(linkAnnotationProperties, payload), is(sameInstance(linkAnnotationCreator)));

		verifyAll();
	}

	@Test(expected = LinkBuilderException.class)
	public void get_linkAnnotationPropertiesPayloadCanCreateFalse() {
		expect(linkAnnotationCreator.canCreate(linkAnnotationProperties, payload)).andReturn(false);

		replayAll();

		assertThat(linkAnnotationCreatorRegistryImpl.get(linkAnnotationProperties, payload), is(nullValue()));

		verifyAll();
	}

	@Test
	public void get_MethodCallPayloadCanCreateTrue() {
		expect(linkAnnotationCreator.canCreate(methodCall, payload)).andReturn(true);

		replayAll();

		assertThat(linkAnnotationCreatorRegistryImpl.get(methodCall, payload), is(sameInstance(linkAnnotationCreator)));

		verifyAll();
	}

	@Test(expected = LinkBuilderException.class)
	public void get_MethodCallPayloadCanCreateFalse() {
		expect(linkAnnotationCreator.canCreate(methodCall, payload)).andReturn(false);

		replayAll();

		assertThat(linkAnnotationCreatorRegistryImpl.get(methodCall, payload), is(nullValue()));

		verifyAll();
	}
}