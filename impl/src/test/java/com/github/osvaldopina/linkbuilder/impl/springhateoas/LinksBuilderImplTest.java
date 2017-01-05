package com.github.osvaldopina.linkbuilder.impl.springhateoas;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import com.github.osvaldopina.linkbuilder.LinkBuilder;
import com.github.osvaldopina.linkbuilder.extension.LinkBuilderExtensionFactoryRegistry;
import com.github.osvaldopina.linkbuilder.fromcall.currentcallrecorder.CurrentCallLocator;
import com.github.osvaldopina.linkbuilder.linkcreator.linkbuilder.LinkPropertiesLinkCreators;
import org.easymock.EasyMockRule;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class LinksBuilderImplTest extends EasyMockSupport {

	@Rule
	public EasyMockRule mocks = new EasyMockRule(this);

	@Mock
	private Object payload;

	@Mock
	private CurrentCallLocator currentCallLocator;

	@Mock
	private LinkPropertiesLinkCreators linkPropertiesLinkCreators;

	@Mock
	private LinkBuilderExtensionFactoryRegistry linkBuilderExtensionFactoryRegistry;

	private LinksBuilderImpl linksBuilderImpl;


	@Before
	public void setUp() {
		linksBuilderImpl = new LinksBuilderImpl(currentCallLocator, linkPropertiesLinkCreators,
				linkBuilderExtensionFactoryRegistry, payload);
	}

	@Test
	public void link() {

		replayAll();

		LinkBuilder linkBuilder = linksBuilderImpl.link();

		verifyAll();

		assertThat(linkBuilder.getClass(), is(typeCompatibleWith(SpringHateoasLinkBuilderImpl.class)));
		SpringHateoasLinkBuilderImpl springHateoasLinkBuilderImpl = (SpringHateoasLinkBuilderImpl) linkBuilder;

		assertThat(springHateoasLinkBuilderImpl.getCurrentCallLocator(), is(currentCallLocator));
		assertThat(springHateoasLinkBuilderImpl.getLinkPropertiesLinkCreators(), is(linkPropertiesLinkCreators));
		assertThat(springHateoasLinkBuilderImpl.getLinkBuilderExtensionFactoryRegistry(), is(linkBuilderExtensionFactoryRegistry));
		assertThat(springHateoasLinkBuilderImpl.getPayload() , is(payload));
		assertThat(springHateoasLinkBuilderImpl.getLinksBuilder().getClass(), is(typeCompatibleWith(LinksBuilderImpl.class)));
		assertThat((LinksBuilderImpl) springHateoasLinkBuilderImpl.getLinksBuilder(),is(sameInstance(linksBuilderImpl)));

	}




}