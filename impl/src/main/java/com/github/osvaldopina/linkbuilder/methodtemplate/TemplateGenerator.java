package com.github.osvaldopina.linkbuilder.methodtemplate;

import com.damnhandy.uri.template.UriTemplate;
import com.github.osvaldopina.linkbuilder.argumentresolver.ArgumentResolvers;

import java.lang.reflect.Method;

/**
 * Generates a UriTemplate for a given annotated method
 */
public interface TemplateGenerator {

    /**
     * Generates a UriTemplate for a given annotated method
     *
     * @param method Annotated method
     *
     * @return UriTemplate Template for given method
     */
    UriTemplate generate(Method method);
}
