package com.github.osvaldopina.linkbuilder.linkdestination.springhateoas;

import com.github.osvaldopina.linkbuilder.LinkBuilderException;
import com.github.osvaldopina.linkbuilder.resoucemethod.ResourceMethodRegistry;
import com.github.osvaldopina.linkbuilder.linkdestination.LinkDestinationRegistry;
import com.github.osvaldopina.linkbuilder.linkdestination.LinkDestinationRegistryFactory;
import com.github.osvaldopina.linkbuilder.utils.IntrospectionUtils;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class LinkDestinationRegistryFactoryImpl implements LinkDestinationRegistryFactory {

    private IntrospectionUtils introspectionUtils;

    private DestinationIdentityFactorty destinationIdentityFactorty = DestinationIdentityFactorty.INSTANCE;

    public LinkDestinationRegistryFactoryImpl(IntrospectionUtils introspectionUtils) {
        this.introspectionUtils = introspectionUtils;
    }



    @Override
    public LinkDestinationRegistry createDestinationRegistry(ResourceMethodRegistry resourceMethodRegistry) {
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
//            throw new LinkBuilderException("Could not determine destination for method " + method);
        }

        return new LinkDestinationRegistryImpl(destinations);
    }

}
