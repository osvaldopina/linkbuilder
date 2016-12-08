package com.github.osvaldopina.linkbuilder.resoucemethod.springhateoas;

import com.github.osvaldopina.linkbuilder.resoucemethod.ResourceMethodRegistry;

import java.lang.reflect.Method;
import java.util.*;

public class ResourceMethodRegistryImpl implements ResourceMethodRegistry {


    private List<Method> resourceMethods = new ArrayList<Method>();

    public ResourceMethodRegistryImpl(List<Method> resourceMethods) {
        this.resourceMethods = new ArrayList<Method>(resourceMethods);
    }

    @Override
    public Collection<Method> getResourceMethods() {
        return Collections.unmodifiableList(resourceMethods);
    }

}
