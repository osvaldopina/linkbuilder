package com.github.osvaldopina.linkbuilder.template.springhateoas;

import com.github.osvaldopina.linkbuilder.resoucemethod.ResourceMethodRegistry;
import com.github.osvaldopina.linkbuilder.template.Template;
import com.github.osvaldopina.linkbuilder.template.generation.TemplateGenerator;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

class TemplateRegistryFactory {

    public static final TemplateRegistryFactory INSTANCE = new TemplateRegistryFactory();

     TemplateRegistryFactory() {
    }


    public Map<Method,Template> createTemplateRegistry(TemplateGenerator templateGenerator, ResourceMethodRegistry resourceMethodRegistry) {

        Map<Method,Template> templates = new HashMap<Method, Template>();

        for (Method method : resourceMethodRegistry.getResourceMethods()) {
            templates.put(method, templateGenerator.generate(method));
        }
        return templates;
    }
}
