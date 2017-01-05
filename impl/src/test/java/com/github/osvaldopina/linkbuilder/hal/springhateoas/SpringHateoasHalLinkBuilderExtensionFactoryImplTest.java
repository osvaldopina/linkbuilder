package com.github.osvaldopina.linkbuilder.hal.springhateoas;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

import com.github.osvaldopina.linkbuilder.LinkBuilder;
import com.github.osvaldopina.linkbuilder.hal.HalLinkBuilder;
import org.junit.Test;

public class SpringHateoasHalLinkBuilderExtensionFactoryImplTest {


	private SpringHateoasHalLinkBuilderExtensionFactoryImpl springHateoasHalLinkBuilderExtensionFactory = new
			SpringHateoasHalLinkBuilderExtensionFactoryImpl();


	public void SpringHateoasHalLinkBuilderExtensionFactoryImpl() {
		springHateoasHalLinkBuilderExtensionFactory = new SpringHateoasHalLinkBuilderExtensionFactoryImpl();
	}

	@Test
	public void canCreateExtension() {
		assertThat(springHateoasHalLinkBuilderExtensionFactory.canCreateExtension(HalLinkBuilder.class), is(true));

	}

	@Test
	public void canCreateExtension_extensionTypeIsNotHalLinkBuilder() {
		assertThat(springHateoasHalLinkBuilderExtensionFactory.canCreateExtension(LinkBuilder.class), is(false));
	}


}