package com.github.osvaldopina.linkbuilder.template.generation.argumentresolver.core;

import com.github.osvaldopina.linkbuilder.template.VariablesFactory;
import com.github.osvaldopina.linkbuilder.template.generation.argumentresolver.ArgumentResolver;
import com.github.osvaldopina.linkbuilder.template.Variable;
import com.github.osvaldopina.linkbuilder.template.Variables;
import com.github.osvaldopina.linkbuilder.utils.IntrospectionUtils;

import java.lang.reflect.Method;
import java.util.Arrays;

public class RequestBodyArgumentResolver implements ArgumentResolver{

    private IntrospectionUtils introspectionUtils;

    private VariablesFactory variablesFactory = VariablesFactory.INSTANCE;

    public RequestBodyArgumentResolver(IntrospectionUtils introspectionUtils) {
        this.introspectionUtils = introspectionUtils;
    }

    @Override
    public boolean resolveFor(Method method, int parameterIndex) {
        return introspectionUtils.isRequestBodyVariableParameter(method, parameterIndex);
    }

    @Override
    public Variables create(Method method, int parameterIndex) {
        return variablesFactory.create();
    }

}
