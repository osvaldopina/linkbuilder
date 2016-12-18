package com.github.osvaldopina.linkbuilder.linkcreator.linkbuilder.impl;

import static org.easymock.EasyMock.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.util.Arrays;

import com.github.osvaldopina.linkbuilder.LinkBuilderException;
import com.github.osvaldopina.linkbuilder.LinkProperties;
import com.github.osvaldopina.linkbuilder.linkcreator.linkbuilder.LinkPropertiesLinkCreator;
import org.easymock.EasyMockRule;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class LinkPropertiesLinkCreatorsImplTest extends EasyMockSupport {

	@Rule
	public EasyMockRule mocks = new EasyMockRule(this);

	@Mock
	LinkPropertiesLinkCreator linkPropertiesLinkCreator;

	LinkPropertiesLinkCreatorsImpl linkPropertiesLinkCreatorsImpl;

	@Mock
	LinkProperties linkProperties;

	@Before
	public void setUp() {
		linkPropertiesLinkCreatorsImpl = new LinkPropertiesLinkCreatorsImpl(Arrays.asList(linkPropertiesLinkCreator));
	}

	@Test
	public void get() throws Exception {
		expect(linkPropertiesLinkCreator.canCreate(linkProperties)).andReturn(true);

		replayAll();

		assertThat(linkPropertiesLinkCreatorsImpl.get(linkProperties), is(linkPropertiesLinkCreator));

		verifyAll();
	}

	@Test(expected = LinkBuilderException.class)
	public void get_canCreateFalse() throws Exception {
		expect(linkPropertiesLinkCreator.canCreate(linkProperties)).andReturn(false);

		replayAll();

		assertThat(linkPropertiesLinkCreatorsImpl.get(linkProperties), is(nullValue()));

		verifyAll();
	}

}