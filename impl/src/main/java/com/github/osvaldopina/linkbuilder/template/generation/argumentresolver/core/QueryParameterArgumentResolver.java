package com.github.osvaldopina.linkbuilder.template.generation.argumentresolver.core;

import com.github.osvaldopina.linkbuilder.template.Variable;
import com.github.osvaldopina.linkbuilder.template.VariableType;
import com.github.osvaldopina.linkbuilder.template.Variables;
import com.github.osvaldopina.linkbuilder.template.VariablesFactory;
import com.github.osvaldopina.linkbuilder.template.generation.argumentresolver.ArgumentResolver;
import com.github.osvaldopina.linkbuilder.utils.IntrospectionUtils;

import java.lang.reflect.Method;
import java.util.Arrays;

public class QueryParameterArgumentResolver implements ArgumentResolver {

    private IntrospectionUtils introspectionUtils;

    private VariablesFactory variablesFactory = VariablesFactory.INSTANCE;

    public QueryParameterArgumentResolver(IntrospectionUtils introspectionUtils) {
        this.introspectionUtils = introspectionUtils;
    }

    @Override
    public boolean resolveFor(Method method, int parameterIndex) {
        return introspectionUtils.isQueryVariableParameter(method, parameterIndex);
    }

    @Override
    public Variables create(Method method, int parameterIndex) {
        String name = introspectionUtils.getQueryVariableName(method, parameterIndex);

        return variablesFactory.create(Arrays.asList(new Variable(name, VariableType.QUERY, method, parameterIndex)));
    }

}
