package com.github.osvaldopina.linkbuilder.linkdestination.springhateoas;

import com.github.osvaldopina.linkbuilder.resoucemethod.ResourceMethodRegistry;
import com.github.osvaldopina.linkbuilder.utils.IntrospectionUtils;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

class LinkDestinationRegistryFactory {

    public static final LinkDestinationRegistryFactory INSTANCE = new LinkDestinationRegistryFactory();

    LinkDestinationRegistryFactory() {

    }

    private DestinationIdentityFactorty destinationIdentityFactorty = DestinationIdentityFactorty.INSTANCE;

    public Map<String, Method> createDestinationRegistry(IntrospectionUtils introspectionUtils,
                                                             ResourceMethodRegistry resourceMethodRegistry) {
        Map<String, Method> destinations = new HashMap<String, Method>();

        for (Method method : resourceMethodRegistry.getResourceMethods()) {
            String rel = introspectionUtils.getMethodRel(method);
            String destination;
            if (rel != null) {
                destination = destinationIdentityFactorty.destination(method.getDeclaringClass(), rel);
                destinations.put(destination, method);
            }
            else {
                destination = introspectionUtils.getMethodDestination(method);
                if (destination != null) {
                    destinations.put(destination, method);
                }
            }
        }

        return destinations;
    }

}
