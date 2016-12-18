package com.github.osvaldopina.linkbuilder.linkdestination.springhateoas;

import com.github.osvaldopina.linkbuilder.LinkBuilderException;
import com.github.osvaldopina.linkbuilder.linkdestination.LinkDestinationRegistry;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class LinkDestinationRegistryImpl implements LinkDestinationRegistry {

    private Map<String, Method> destinations = new HashMap<String, Method>();

    private DestinationIdentityFactorty destinationIdentityFactorty = new DestinationIdentityFactorty();

    public LinkDestinationRegistryImpl(Map<String, Method> destinations) {
        this.destinations = new HashMap<String, Method>(destinations);
    }

    @Override
    public Method getTemplatedMethod(String destination) {

        Method method = destinations.get(destination);
        if (method == null) {
            throw new LinkBuilderException("Could not find a linkdestination " + destination);
        }
        return method;
    }

    public Map<String, Method> getDestinations() {
        return Collections.unmodifiableMap(destinations);
    }

}
