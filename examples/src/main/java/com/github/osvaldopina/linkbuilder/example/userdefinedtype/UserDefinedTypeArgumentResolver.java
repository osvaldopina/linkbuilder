package com.github.osvaldopina.linkbuilder.example.userdefinedtype;

import com.github.osvaldopina.linkbuilder.template.Variable;
import com.github.osvaldopina.linkbuilder.template.VariableType;
import com.github.osvaldopina.linkbuilder.template.Variables;
import com.github.osvaldopina.linkbuilder.template.generation.argumentresolver.ArgumentResolver;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;

@Component
public class UserDefinedTypeArgumentResolver implements ArgumentResolver{

    @Override
    public boolean resolveFor(Method method, int parameterIndex) {
        return UserDefinedType.class.isAssignableFrom(method.getParameterTypes()[parameterIndex]);
    }

    @Override
    public Variables create(Method method, int parameterIndex) {
        return new Variables(Arrays.asList(
                new Variable("value1", VariableType.QUERY, method, parameterIndex),
                new Variable("value2", VariableType.QUERY, method, parameterIndex)
        ));
    }

}
