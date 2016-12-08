package com.github.osvaldopina.linkbuilder.template;

import com.github.osvaldopina.linkbuilder.resoucemethod.ResourceMethodRegistry;

public interface TemplateRegistryFactory {

    TemplateRegistry createTemplateRegistry(ResourceMethodRegistry resourceMethodRegistry);
}
