package com.github.osvaldopina.linkbuilder.methodtemplate;

import com.damnhandy.uri.template.UriTemplate;

import java.lang.reflect.Method;


/**
 * Creates a new template for a given method or link annotated target
 */
public interface UriTemplateMethodMappings {

    /**
     * Creates a new template for a given method
     *
     * @param method Method to gatter template information
     * @return UriTemplate correspondent to method http path
     */
    UriTemplate createTemplateForMethod(Method method);

    /**
     * Creates a new Template for a given Controller and target
     *
     * @param controller Controller class
     * @param rel t
     * @return UriTemplate
     */
    UriTemplate createTemplateForLinkTarget(Class<?> controller, String rel);

}
