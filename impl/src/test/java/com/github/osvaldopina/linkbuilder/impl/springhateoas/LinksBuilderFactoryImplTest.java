package com.github.osvaldopina.linkbuilder.impl.springhateoas;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

import com.github.osvaldopina.linkbuilder.LinksBuilder;
import com.github.osvaldopina.linkbuilder.extension.LinkBuilderExtensionFactoryRegistry;
import com.github.osvaldopina.linkbuilder.fromcall.currentcallrecorder.CurrentCallLocator;
import com.github.osvaldopina.linkbuilder.linkcreator.linkbuilder.LinkPropertiesLinkCreators;
import org.easymock.EasyMockRule;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class LinksBuilderFactoryImplTest extends EasyMockSupport {

	@Rule
	public EasyMockRule mocks = new EasyMockRule(this);

	@Mock
	CurrentCallLocator currentCallLocator;

	@Mock
	LinkPropertiesLinkCreators linkPropertiesLinkCreators;

	@Mock
	LinkBuilderExtensionFactoryRegistry linkBuilderExtensionFactoryRegistry;

	private Object payload = new Object();

	LinksBuilderFactoryImpl linksBuilderFactoryImpl;


	@Before
	public void setUp() {
		linksBuilderFactoryImpl = new LinksBuilderFactoryImpl(currentCallLocator,
				linkPropertiesLinkCreators, linkBuilderExtensionFactoryRegistry);
	}

	@Test
	public void create() {

		replayAll();

		LinksBuilder linksBuilder = linksBuilderFactoryImpl.create();

		verifyAll();

		assertThat(linksBuilder.getClass(), is(typeCompatibleWith(LinksBuilderImpl.class)));
		LinksBuilderImpl linksBuilderImpl = (LinksBuilderImpl) linksBuilder;

		assertThat(linksBuilderImpl.getCurrentCallLocator(), is(sameInstance(currentCallLocator)));
		assertThat(linksBuilderImpl.getLinkPropertiesLinkCreators(), is(sameInstance(linkPropertiesLinkCreators)));
		assertThat(linksBuilderImpl.getLinkBuilderExtensionFactoryRegistry(), is(sameInstance(linkBuilderExtensionFactoryRegistry)));
		assertThat(linksBuilderImpl.getPayload(), is(nullValue()));

	}


	@Test
	public void create_payload() {

		replayAll();

		LinksBuilder linksBuilder = linksBuilderFactoryImpl.create(payload);

		verifyAll();

		assertThat(linksBuilder.getClass(), is(typeCompatibleWith(LinksBuilderImpl.class)));
		LinksBuilderImpl linksBuilderImpl = (LinksBuilderImpl) linksBuilder;

		assertThat(linksBuilderImpl.getCurrentCallLocator(), is(sameInstance(currentCallLocator)));
		assertThat(linksBuilderImpl.getLinkPropertiesLinkCreators(), is(sameInstance(linkPropertiesLinkCreators)));
		assertThat(linksBuilderImpl.getLinkBuilderExtensionFactoryRegistry(), is(sameInstance(linkBuilderExtensionFactoryRegistry)));
		assertThat(linksBuilderImpl.getPayload(), is(sameInstance(payload)));

	}

}