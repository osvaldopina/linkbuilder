package com.github.osvaldopina.linkbuilder.template.springhateoas;

import com.github.osvaldopina.linkbuilder.LinkBuilderException;
import com.github.osvaldopina.linkbuilder.annotation.GenerateUriTemplateFor;
import com.github.osvaldopina.linkbuilder.resoucemethod.ResourceMethodRegistry;
import com.github.osvaldopina.linkbuilder.template.Template;
import com.github.osvaldopina.linkbuilder.template.TemplateRegistry;
import com.github.osvaldopina.linkbuilder.template.generation.TemplateGenerator;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class TemplateRegistryImpl implements TemplateRegistry {

    private Map<Method, Template> templates;

    private ResourceMethodRegistry resourceMethodRegistry;
    private TemplateGenerator templateGenerator;

    private TemplateRegistryFactory templateRegistryFactory = TemplateRegistryFactory.INSTANCE;

    public TemplateRegistryImpl(ResourceMethodRegistry resourceMethodRegistry, TemplateGenerator templateGenerator) {
        this.resourceMethodRegistry = resourceMethodRegistry;
        this.templateGenerator = templateGenerator;
    }


    @Override
    public Template getTemplate(Method method) {


        if (templates == null) {
               templates = templateRegistryFactory.createTemplateRegistry(templateGenerator, resourceMethodRegistry);
        }

        Template template = templates.get(method);

        if (template == null) {
            throw new LinkBuilderException(
                    "Could not generate template for controller " +
                            method + " check if its annotated with @" + GenerateUriTemplateFor.class.getName());
        }
        return template;
    }

}
