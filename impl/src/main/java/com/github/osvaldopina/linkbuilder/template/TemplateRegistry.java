package com.github.osvaldopina.linkbuilder.template;

import java.lang.reflect.Method;

public interface TemplateRegistry {

    Template getTemplate(Method method);
}
