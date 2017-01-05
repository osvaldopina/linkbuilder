package com.github.osvaldopina.linkbuilder.template.springhateoas;

import com.github.osvaldopina.linkbuilder.resoucemethod.ResourceMethodRegistry;
import com.github.osvaldopina.linkbuilder.template.Template;
import com.github.osvaldopina.linkbuilder.template.TemplateRegistry;
import com.github.osvaldopina.linkbuilder.template.TemplateRegistryFactory;
import com.github.osvaldopina.linkbuilder.template.generation.TemplateGenerator;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class TemplateRegistryFactoryImpl implements TemplateRegistryFactory {

    // TODO remover
//    private TemplateGenerator templateGenerator;
//
//    public TemplateRegistryFactoryImpl(TemplateGenerator templateGenerator) {
//        this.templateGenerator = templateGenerator;
//    }
//
//
//    @Override
//    public TemplateRegistry createTemplateRegistry(ResourceMethodRegistry resourceMethodRegistry) {
//
//        Map<Method,Template> templates = new HashMap<Method, Template>();
//
//        for (Method method : resourceMethodRegistry.getResourceMethods()) {
//            templates.put(method, templateGenerator.generate(method));
//        }
//        return new TemplateRegistryImpl(templates);
//    }
}
