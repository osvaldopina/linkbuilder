package com.github.osvaldopina.linkbuilder.example.extensions.templategenerator;

import com.damnhandy.uri.template.UriTemplate;
import com.github.osvaldopina.linkbuilder.methodtemplate.TemplateGenerator;
import com.github.osvaldopina.linkbuilder.expression.ExpressionExecutor;

import java.lang.reflect.Method;

public class CustomTemplateGenerator implements TemplateGenerator {

    @Override
    public UriTemplate generate(Method method) {
        return UriTemplate.fromTemplate("/custom-template{/query}");
    }
}
