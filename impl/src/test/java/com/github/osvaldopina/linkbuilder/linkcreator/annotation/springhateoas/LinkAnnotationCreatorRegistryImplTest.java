package com.github.osvaldopina.linkbuilder.linkcreator.annotation.springhateoas;

import static org.easymock.EasyMock.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.util.Arrays;

import com.github.osvaldopina.linkbuilder.LinkBuilderException;
import com.github.osvaldopina.linkbuilder.annotation.linkcreator.LinkAnnotationCreator;
import com.github.osvaldopina.linkbuilder.annotation.linkcreator.springhateoas.LinkAnnotationCreatorRegistryImpl;
import com.github.osvaldopina.linkbuilder.annotation.reader.properties.LinkAnnotationProperties;
import com.github.osvaldopina.linkbuilder.fromcall.MethodCall;
import org.easymock.EasyMockRule;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;

@Ignore
public class LinkAnnotationCreatorRegistryImplTest extends EasyMockSupport {

	@Rule
	public EasyMockRule mocks = new EasyMockRule(this);

	private LinkAnnotationCreatorRegistryImpl linkAnnotationCreatorRegistryImpl;

	@Mock
	private LinkAnnotationCreator linkAnnotationCreator;

	@Mock
	private LinkAnnotationProperties linkAnnotationProperties;

	@Mock
	private Object resource;

	@Mock
	private MethodCall methodCall;

	public LinkAnnotationCreatorRegistryImplTest() {
	}

	@Before
	public void setUp() {
		linkAnnotationCreatorRegistryImpl = new LinkAnnotationCreatorRegistryImpl(Arrays.asList(linkAnnotationCreator));
	}


	@Test
	public void get_linkAnnotationPropertiesResourceCanCreateTrue() {
	//	expect(linkAnnotationCreator.canCreate(linkAnnotationProperties, resource)).andReturn(true);

		replayAll();

	//	assertThat(linkAnnotationCreatorRegistryImpl.get(linkAnnotationProperties, resource), is(sameInstance(linkAnnotationCreator)));

		verifyAll();
	}

	@Test(expected = LinkBuilderException.class)
	public void get_linkAnnotationPropertiesResourceCanCreateFalse() {
	//	expect(linkAnnotationCreator.canCreate(linkAnnotationProperties, resource)).andReturn(false);

		replayAll();

	//	assertThat(linkAnnotationCreatorRegistryImpl.get(linkAnnotationProperties, resource), is(nullValue()));

		verifyAll();
	}

	@Test
	public void get_MethodCallResourceCanCreateTrue() {
	//	expect(linkAnnotationCreator.canCreate(methodCall, resource)).andReturn(true);

		replayAll();

	//	assertThat(linkAnnotationCreatorRegistryImpl.get(methodCall, resource), is(sameInstance(linkAnnotationCreator)));

		verifyAll();
	}

	@Test(expected = LinkBuilderException.class)
	public void get_MethodCallResourceCanCreateFalse() {
	//	expect(linkAnnotationCreator.canCreate(methodCall, resource)).andReturn(false);

		replayAll();

	//	assertThat(linkAnnotationCreatorRegistryImpl.get(methodCall, resource), is(nullValue()));

		verifyAll();
	}
}