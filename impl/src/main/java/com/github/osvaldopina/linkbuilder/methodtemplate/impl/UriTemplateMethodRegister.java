package com.github.osvaldopina.linkbuilder.methodtemplate.impl;

import com.damnhandy.uri.template.UriTemplate;
import com.github.osvaldopina.linkbuilder.LinkBuilderException;
import com.github.osvaldopina.linkbuilder.utils.IntrospectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

class UriTemplateMethodRegister {

    private final Log logger = LogFactory.getLog(getClass());

    private IntrospectionUtils introspectionUtils;

    private Map<Method,UriTemplate> methodTemplates = new HashMap<Method, UriTemplate>();

    private Map<String,UriTemplate> targetTemplates = new HashMap<String, UriTemplate>();

    UriTemplateMethodRegister(IntrospectionUtils introspectionUtils) {
        this.introspectionUtils = introspectionUtils;
    }

    public void register(Method method, UriTemplate uriTemplate) {

        methodTemplates.put(method, uriTemplate);

        String rel = introspectionUtils.getMethodRel(method);

        targetTemplates.put(method.getDeclaringClass().getName() + ":" + rel, uriTemplate);
    }

    public UriTemplate get(Method method) {
        UriTemplate template = methodTemplates.get(method);

        if (template == null) {
            throw new LinkBuilderException("Could not find template for method " +
                    method +
                    ".\n Was this method annotated with @EnableSelfFromCurrentCall or @GenerateUriTemplateFor?");
        }
        else {
            return template;
        }

    }

    public UriTemplate get(Class<?> controller, String rel) {
        UriTemplate template = targetTemplates.get(controller.getName() + ":" + rel);

        if (template == null) {
            throw new LinkBuilderException("Could not find template for @GenerateUriTemplateFor controller " +
                    controller + " with rel " + rel +
                    ".\n Was this method annotated with @GenerateUriTemplateFor?");
        }
        else {
            return template;
        }

    }


}
