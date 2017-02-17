package com.github.osvaldopina.linkbuilder.impl.springhateoas;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.github.osvaldopina.linkbuilder.LinkBuilder;
import com.github.osvaldopina.linkbuilder.LinksBuilder;
import com.github.osvaldopina.linkbuilder.extension.LinkBuilderExtensionFactoryRegistry;
import com.github.osvaldopina.linkbuilder.linkcreator.linkbuilder.LinkPropertiesLinkCreators;

/**
 * Builder for Links. This builder should be used to create a list of <code>Link</code>. The controller <code>link</code>
 * should be used to create a builder for a link.
 */

class LinksBuilderImpl implements LinksBuilder {

	private Object resource;
	private List<SpringHateoasLinkBuilderImpl> linkBuilders = new ArrayList<SpringHateoasLinkBuilderImpl>();
	private LinkPropertiesLinkCreators linkPropertiesLinkCreators;
	private LinkBuilderExtensionFactoryRegistry linkBuilderExtensionFactoryRegistry;

	protected LinksBuilderImpl(LinkPropertiesLinkCreators linkPropertiesLinkCreators,
			LinkBuilderExtensionFactoryRegistry linkBuilderExtensionFactoryRegistry) {
		this(linkPropertiesLinkCreators, linkBuilderExtensionFactoryRegistry, null);
	}

	protected LinksBuilderImpl(LinkPropertiesLinkCreators linkPropertiesLinkCreators,
			LinkBuilderExtensionFactoryRegistry linkBuilderExtensionFactoryRegistry,Object resource) {

		this.linkPropertiesLinkCreators = linkPropertiesLinkCreators;
		this.linkBuilderExtensionFactoryRegistry = linkBuilderExtensionFactoryRegistry;
		this.resource = resource;
	}

	@Override
	public LinkBuilder link() {
		SpringHateoasLinkBuilderImpl linkBuilder = new SpringHateoasLinkBuilderImpl(
				this,
				linkPropertiesLinkCreators,
				linkBuilderExtensionFactoryRegistry,
				resource
		);
		linkBuilders.add(linkBuilder);
		return linkBuilder;
	}

	@Override
	public void buildAndSetAll() {

		for (LinkBuilder linkBuilder : linkBuilders) {
			linkBuilder.builAndSet();
		}
	}

	public Object getResource() {
		return resource;
	}

	public List<SpringHateoasLinkBuilderImpl> getLinkBuilders() {
		return Collections.unmodifiableList(linkBuilders);
	}

	public LinkPropertiesLinkCreators getLinkPropertiesLinkCreators() {
		return linkPropertiesLinkCreators;
	}

	public LinkBuilderExtensionFactoryRegistry getLinkBuilderExtensionFactoryRegistry() {
		return linkBuilderExtensionFactoryRegistry;
	}
}