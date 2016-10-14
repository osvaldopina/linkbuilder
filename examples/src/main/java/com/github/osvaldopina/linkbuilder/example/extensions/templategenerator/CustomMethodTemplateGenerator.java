package com.github.osvaldopina.linkbuilder.example.extensions.templategenerator;

import com.damnhandy.uri.template.UriTemplate;
import com.github.osvaldopina.linkbuilder.methodtemplate.templategenerator.MethodTemplateGenerator;

import java.lang.reflect.Method;

public class CustomMethodTemplateGenerator implements MethodTemplateGenerator {

    @Override
    public UriTemplate generate(Method method) {
        return UriTemplate.fromTemplate("/custom-template{/query}");
    }
}
