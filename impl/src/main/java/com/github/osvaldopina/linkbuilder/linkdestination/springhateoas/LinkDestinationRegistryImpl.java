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

	private LinkDestinationRegistryFactory linkDestinationRegistryFactory = LinkDestinationRegistryFactory.INSTANCE;

	private IntrospectionUtils introspectionUtils;

	public LinkDestinationRegistryImpl(ResourceMethodRegistry resourceMethodRegistry, IntrospectionUtils introspectionUtils) {
		this.resourceMethodRegistry = resourceMethodRegistry;
		this.introspectionUtils = introspectionUtils;
	}

	@Override
	public Method getTemplatedMethod(String destination) {

		if (destinations == null) {
			destinations = linkDestinationRegistryFactory.createDestinationRegistry(introspectionUtils, resourceMethodRegistry);
		}

		Method method = destinations.get(destination);
		if (method == null) {
			throw new LinkBuilderException("Could not find a linkdestination " + destination);
		}
		return method;
	}


}
