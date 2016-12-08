package com.github.osvaldopina.linkbuilder.linkdestination;

import com.github.osvaldopina.linkbuilder.resoucemethod.ResourceMethodRegistry;

public interface LinkDestinationRegistryFactory {

    LinkDestinationRegistry createDestinationRegistry(ResourceMethodRegistry resourceMethodRegistry);

}
