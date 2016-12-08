package com.github.osvaldopina.linkbuilder.template.generation.argumentresolver.springhateoas;

import com.damnhandy.uri.template.UriTemplate;
import com.github.osvaldopina.linkbuilder.template.Variable;
import com.github.osvaldopina.linkbuilder.template.VariableType;
import com.github.osvaldopina.linkbuilder.template.Variables;
import com.github.osvaldopina.linkbuilder.template.generation.argumentresolver.ArgumentResolver;
import org.springframework.data.domain.Pageable;
import org.springframework.util.Assert;

import java.lang.reflect.Method;
import java.util.Arrays;

public class PageableArgumentResolver implements ArgumentResolver {

    @Override
    public boolean resolveFor(Method method, int parameterIndex) {

        return Pageable.class.isAssignableFrom(method.getParameterTypes()[parameterIndex]);
    }


    @Override
    public Variables create(Method method, int parameterIndex) {

        return new Variables(
                Arrays.asList(
                        new Variable("page", VariableType.QUERY, method, parameterIndex),
                        new Variable("size", VariableType.QUERY, method, parameterIndex),
                        new Variable("sort", VariableType.QUERY, method, parameterIndex)
                )
        );

    }
}
