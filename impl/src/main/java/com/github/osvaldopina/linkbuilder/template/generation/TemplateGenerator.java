package com.github.osvaldopina.linkbuilder.template.generation;

import com.github.osvaldopina.linkbuilder.template.Template;

import java.lang.reflect.Method;

public interface TemplateGenerator {

    Template generate(Method method);
}
