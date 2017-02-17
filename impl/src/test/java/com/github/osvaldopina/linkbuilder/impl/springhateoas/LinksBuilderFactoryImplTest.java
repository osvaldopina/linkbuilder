package com.github.osvaldopina.linkbuilder.impl.springhateoas;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

import com.github.osvaldopina.linkbuilder.LinksBuilder;
import com.github.osvaldopina.linkbuilder.extension.LinkBuilderExtensionFactoryRegistry;
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

//	@Mock
//	CurrentCallLocator currentCallLocator;

	@Mock
	LinkPropertiesLinkCreators linkPropertiesLinkCreators;

	@Mock
	LinkBuilderExtensionFactoryRegistry linkBuilderExtensionFactoryRegistry;

	private Object resource = new Object();

	LinksBuilderFactoryImpl linksBuilderFactoryImpl;


	@Before
	public void setUp() {
		linksBuilderFactoryImpl = new LinksBuilderFactoryImpl(
				//currentCallLocator,
				linkPropertiesLinkCreators, linkBuilderExtensionFactoryRegistry);
	}

	@Test
	public void create() {

		replayAll();

		LinksBuilder linksBuilder = linksBuilderFactoryImpl.create();

		verifyAll();

		assertThat(linksBuilder.getClass(), is(typeCompatibleWith(LinksBuilderImpl.class)));
		LinksBuilderImpl linksBuilderImpl = (LinksBuilderImpl) linksBuilder;

//		assertThat(linksBuilderImpl.getCurrentCallLocator(), is(sameInstance(currentCallLocator)));
		assertThat(linksBuilderImpl.getLinkPropertiesLinkCreators(), is(sameInstance(linkPropertiesLinkCreators)));
		assertThat(linksBuilderImpl.getLinkBuilderExtensionFactoryRegistry(), is(sameInstance(linkBuilderExtensionFactoryRegistry)));
		assertThat(linksBuilderImpl.getResource(), is(nullValue()));

	}


	@Test
	public void create_resource() {

		replayAll();

		LinksBuilder linksBuilder = linksBuilderFactoryImpl.create(resource);

		verifyAll();

		assertThat(linksBuilder.getClass(), is(typeCompatibleWith(LinksBuilderImpl.class)));
		LinksBuilderImpl linksBuilderImpl = (LinksBuilderImpl) linksBuilder;

//		assertThat(linksBuilderImpl.getCurrentCallLocator(), is(sameInstance(currentCallLocator)));
		assertThat(linksBuilderImpl.getLinkPropertiesLinkCreators(), is(sameInstance(linkPropertiesLinkCreators)));
		assertThat(linksBuilderImpl.getLinkBuilderExtensionFactoryRegistry(), is(sameInstance(linkBuilderExtensionFactoryRegistry)));
		assertThat(linksBuilderImpl.getResource(), is(sameInstance(resource)));

	}

}