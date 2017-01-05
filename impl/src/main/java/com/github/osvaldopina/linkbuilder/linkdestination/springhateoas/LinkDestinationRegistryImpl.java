package com.github.osvaldopina.linkbuilder.linkdestination.springhateoas;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.github.osvaldopina.linkbuilder.LinkBuilderException;
import com.github.osvaldopina.linkbuilder.linkdestination.LinkDestinationRegistry;
import com.github.osvaldopina.linkbuilder.resoucemethod.ResourceMethodRegistry;
import com.github.osvaldopina.linkbuilder.utils.IntrospectionUtils;

public class LinkDestinationRegistryImpl implements LinkDestinationRegistry {

	private ResourceMethodRegistry resourceMethodRegistry;

	private Map<String, Method> destinations;

	private DestinationIdentityFactorty destinationIdentityFactorty = new DestinationIdentityFactorty();

	private IntrospectionUtils introspectionUtils;

	public LinkDestinationRegistryImpl(ResourceMethodRegistry resourceMethodRegistry, IntrospectionUtils introspectionUtils) {
		this.resourceMethodRegistry = resourceMethodRegistry;
		this.introspectionUtils = introspectionUtils;
	}

	@Override
	public Method getTemplatedMethod(String destination) {

		if (destinations == null) {
			destinations = createDestinationRegistry();
		}

		Method method = destinations.get(destination);
		if (method == null) {
			throw new LinkBuilderException("Could not find a linkdestination " + destination);
		}
		return method;
	}

	public Map<String, Method> getDestinations() {
		return Collections.unmodifiableMap(destinations);
	}

	private Map<String, Method> createDestinationRegistry() {
		Map<String, Method> destinations = new HashMap<String, Method>();

		for (Method method : resourceMethodRegistry.getResourceMethods()) {
			String rel = introspectionUtils.getMethodRel(method);
			String destination;
			if (rel != null) {
				destination = destinationIdentityFactorty.destination(method.getDeclaringClass(), rel);
				destinations.put(destination, method);
			} else {
				destination = introspectionUtils.getMethodDestination(method);
				if (destination != null) {
					destinations.put(destination, method);
				}
			}
		}

		return destinations;
	}
}
