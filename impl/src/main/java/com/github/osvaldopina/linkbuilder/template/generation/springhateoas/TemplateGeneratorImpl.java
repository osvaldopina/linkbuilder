package com.github.osvaldopina.linkbuilder.template.generation.springhateoas;

import java.lang.reflect.Method;

import com.github.osvaldopina.linkbuilder.template.Template;
import com.github.osvaldopina.linkbuilder.template.Variables;
import com.github.osvaldopina.linkbuilder.template.generation.TemplateGenerator;
import com.github.osvaldopina.linkbuilder.template.generation.argumentresolver.ArgumentResolverRegistry;

public class TemplateGeneratorImpl implements TemplateGenerator{


    private ArgumentResolverRegistry argumentResolverRegistry;

    private MethodVariableCreator methodVariableCreator = MethodVariableCreator.INSTANCE;

    private MethodTemplateCreator methodTemplateCreator = MethodTemplateCreator.INSTANCE;

    public TemplateGeneratorImpl(ArgumentResolverRegistry argumentResolverRegistry) {
        this.argumentResolverRegistry = argumentResolverRegistry;
    }

    @Override
    public Template generate(Method method) {

        Variables variables = methodVariableCreator.create(argumentResolverRegistry, method);


        return methodTemplateCreator.create(method, variables);
    }

}
