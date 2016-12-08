package com.github.osvaldopina.linkbuilder.extension;

import com.github.osvaldopina.linkbuilder.BaseLinkBuilder;
import com.github.osvaldopina.linkbuilder.LinkBuilder;

public interface LinkBuilderExtensionFactory {

    boolean canCreateExtension(Class<? extends LinkBuilder> extensionType);

    <E extends LinkBuilder> E createExtension(Class<E> extensionType, BaseLinkBuilder current);

}
