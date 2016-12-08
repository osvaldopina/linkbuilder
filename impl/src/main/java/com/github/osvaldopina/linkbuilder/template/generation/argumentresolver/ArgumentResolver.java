package com.github.osvaldopina.linkbuilder.template.generation.argumentresolver;

import com.github.osvaldopina.linkbuilder.template.Variable;
import com.github.osvaldopina.linkbuilder.template.Variables;

import java.lang.reflect.Method;

public interface ArgumentResolver {

    boolean resolveFor(Method method, int parameterIndex);

    Variables create(Method method, int parameterIndex);
}
