package com.github.osvaldopina.linkbuilder.impl.springhateoas;

import com.github.osvaldopina.linkbuilder.BaseLinkBuilder;
import com.github.osvaldopina.linkbuilder.LinksBuilder;
import com.github.osvaldopina.linkbuilder.extension.LinkBuilderExtensionFactoryRegistry;
import com.github.osvaldopina.linkbuilder.linkcreator.linkbuilder.LinkPropertiesLinkCreators;

public class SpringHateoasLinkBuilderImpl extends BaseLinkBuilder  {

// TODO remover fromCurrentCall
    public SpringHateoasLinkBuilderImpl(
            LinksBuilder linksBuilder,
          //  CurrentCallLocator currentCallLocator,
            LinkPropertiesLinkCreators linkPropertiesLinkCreators,
            LinkBuilderExtensionFactoryRegistry linkBuilderExtensionFactoryRegistry,
            Object resource) {

        super(linksBuilder,
             //   currentCallLocator,
                linkPropertiesLinkCreators,
                linkBuilderExtensionFactoryRegistry,
                resource);
    }





}

