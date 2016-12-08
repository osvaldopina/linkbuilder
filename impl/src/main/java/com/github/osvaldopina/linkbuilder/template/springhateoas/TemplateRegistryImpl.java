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

    public TemplateRegistryImpl(Map<Method,Template> templates) {
        this.templates = new HashMap<Method, Template>(templates);
    }


    @Override
    public Template getTemplate(Method method) {
        Template template = templates.get(method);

        if (template == null) {
            throw new LinkBuilderException(
                    "Could not generate template for method " +
                            method + " check if its annotated with @" + GenerateUriTemplateFor.class.getName());
        }
        return template;
    }

}
