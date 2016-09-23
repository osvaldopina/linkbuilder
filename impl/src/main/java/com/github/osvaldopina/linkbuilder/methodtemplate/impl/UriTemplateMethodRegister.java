package com.github.osvaldopina.linkbuilder.methodtemplate.impl;

import com.damnhandy.uri.template.UriTemplate;
import com.github.osvaldopina.linkbuilder.LinkBuilderException;
import com.github.osvaldopina.linkbuilder.utils.TemplateBuilderInstrospectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

class UriTemplateMethodRegister {

    private final Log logger = LogFactory.getLog(getClass());

    private TemplateBuilderInstrospectionUtils templateBuilderInstrospectionUtils = new TemplateBuilderInstrospectionUtils();

    private Map<Method,UriTemplate> methodTemplates = new HashMap<Method, UriTemplate>();

    private Map<String,UriTemplate> targetTemplates = new HashMap<String, UriTemplate>();

    public void register(Method method, UriTemplate uriTemplate) {

        methodTemplates.put(method, uriTemplate);

        String linkTarget = null;


        if (templateBuilderInstrospectionUtils.hasLinkTargetAnnotation(method)) {
            linkTarget = templateBuilderInstrospectionUtils.getLinkTarget(method);
            targetTemplates.put(linkTarget, uriTemplate);
        }
        logger.info("Registered for method:"
                + method
                + ", uri template:"
                + uriTemplate.getTemplate()
                + (linkTarget!= null?(", link target:" + linkTarget):""));

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

    public UriTemplate get(Class<?> controller, String target) {
        UriTemplate template = targetTemplates.get(controller.getName() + ":" + target);

        if (template == null) {
            throw new LinkBuilderException("Could not find template for @LinkTarget controller " +
                    controller + " with target " + target +
                    ".\n Was this method annotated with @EnableSelfFromCurrentCall or @GenerateUriTemplateFor?");
        }
        else {
            return template;
        }

    }


}
