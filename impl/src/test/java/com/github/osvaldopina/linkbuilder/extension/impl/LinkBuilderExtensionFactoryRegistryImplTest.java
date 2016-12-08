package com.github.osvaldopina.linkbuilder.extension.impl;

import static org.easymock.EasyMock.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.util.Arrays;

import com.github.osvaldopina.linkbuilder.LinkBuilder;
import com.github.osvaldopina.linkbuilder.LinkBuilderException;
import com.github.osvaldopina.linkbuilder.annotation.reader.AnnotationReader;
import com.github.osvaldopina.linkbuilder.extension.LinkBuilderExtensionFactory;
import com.github.osvaldopina.linkbuilder.utils.Utils;
import org.easymock.EasyMockRule;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class LinkBuilderExtensionFactoryRegistryImplTest extends EasyMockSupport {

	@Rule
	public EasyMockRule mocks = new EasyMockRule(this);

	@Mock
	LinkBuilderExtensionFactory linkBuilderExtensionFactory;


	LinkBuilderExtensionFactoryRegistryImpl linkBuilderExtensionFactoryRegistryImpl;

	@Before
	public void setUp() {
		linkBuilderExtensionFactoryRegistryImpl = new LinkBuilderExtensionFactoryRegistryImpl(
				Arrays.asList(linkBuilderExtensionFactory));
	}

	@Test
	public void get() throws Exception {
		expect(linkBuilderExtensionFactory.canCreateExtension(LinkBuilder.class)).andReturn(true);

		replayAll();

		assertThat(linkBuilderExtensionFactoryRegistryImpl.get(LinkBuilder.class),
				is(sameInstance(linkBuilderExtensionFactory)));

		verifyAll();

	}

	@Test(expected = LinkBuilderException.class)
	public void get_cannotFindLinkBuilderExtensionFactory() throws Exception {
		expect(linkBuilderExtensionFactory.canCreateExtension(LinkBuilder.class)).andReturn(false);

		replayAll();

		assertThat(linkBuilderExtensionFactoryRegistryImpl.get(LinkBuilder.class),
				is(nullValue()));

		verifyAll();

	}

}