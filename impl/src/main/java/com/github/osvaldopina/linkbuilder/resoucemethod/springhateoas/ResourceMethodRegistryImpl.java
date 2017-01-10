package com.github.osvaldopina.linkbuilder.resoucemethod.springhateoas;

import java.lang.reflect.Method;
import java.util.*;

import com.github.osvaldopina.linkbuilder.resoucemethod.ResourceMethodRegistry;
import com.github.osvaldopina.linkbuilder.utils.IntrospectionUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

public class ResourceMethodRegistryImpl implements ResourceMethodRegistry {


	private List<Method> resourceMethods;

	private RequestMappingHandlerMapping handlerMapping;
	private IntrospectionUtils introspectionUtils;

	private ResourceMethodRegistryFactory resourceMethodRegistryFactory = ResourceMethodRegistryFactory.INSTANCE;

	public ResourceMethodRegistryImpl(RequestMappingHandlerMapping handlerMapping, IntrospectionUtils introspectionUtils) {
		this.handlerMapping = handlerMapping;
		this.introspectionUtils = introspectionUtils;
	}

	@Override
	public Collection<Method> getResourceMethods() {
		if (resourceMethods == null) {
			resourceMethods = resourceMethodRegistryFactory.create(introspectionUtils, handlerMapping);
		}
		return Collections.unmodifiableList(resourceMethods);
	}
}
