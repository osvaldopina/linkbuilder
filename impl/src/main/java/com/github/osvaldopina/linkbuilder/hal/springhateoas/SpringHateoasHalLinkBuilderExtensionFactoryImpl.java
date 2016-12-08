package com.github.osvaldopina.linkbuilder.hal.springhateoas;

import com.github.osvaldopina.linkbuilder.BaseLinkBuilder;
import com.github.osvaldopina.linkbuilder.LinkBuilder;
import com.github.osvaldopina.linkbuilder.extension.LinkBuilderExtensionFactory;
import com.github.osvaldopina.linkbuilder.hal.HalLinkBuilder;
import com.github.osvaldopina.linkbuilder.hal.HalLinkBuilderImpl;
import com.github.osvaldopina.linkbuilder.hal.HalLinkProperties;

public class SpringHateoasHalLinkBuilderExtensionFactoryImpl implements LinkBuilderExtensionFactory {


    @Override
    public boolean canCreateExtension(Class<? extends LinkBuilder> extensionType) {
        return HalLinkBuilder.class == extensionType;
    }

    @Override
    public <E extends LinkBuilder> E createExtension(Class<E> extensionType, BaseLinkBuilder current) {
        return (E) new HalLinkBuilderImpl(current);
    }
}
