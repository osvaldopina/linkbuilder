package com.github.osvaldopina.linkbuilder.extension;

import com.github.osvaldopina.linkbuilder.LinkBuilder;

public interface LinkBuilderExtensionFactoryRegistry {

    LinkBuilderExtensionFactory get(Class<? extends LinkBuilder> linkBuilderExtensionType);
}
