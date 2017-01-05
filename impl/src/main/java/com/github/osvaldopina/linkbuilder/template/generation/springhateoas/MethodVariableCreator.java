package com.github.osvaldopina.linkbuilder.template.generation.springhateoas;

import java.lang.reflect.Method;

import com.github.osvaldopina.linkbuilder.template.Variables;
import com.github.osvaldopina.linkbuilder.template.VariablesFactory;
import com.github.osvaldopina.linkbuilder.template.generation.argumentresolver.ArgumentResolver;
import com.github.osvaldopina.linkbuilder.template.generation.argumentresolver.ArgumentResolverRegistry;

public class MethodVariableCreator {

	VariablesFactory variablesFactory = VariablesFactory.INSTANCE;

	public static final MethodVariableCreator INSTANCE = new MethodVariableCreator();


	MethodVariableCreator() {

	}

	public Variables create(ArgumentResolverRegistry argumentResolverRegistry, Method method) {
		ArgumentResolver argumentResolver;

		Variables variables = variablesFactory.create();
		for(int i=0; i < method.getParameterTypes().length; i++) {
			argumentResolver = argumentResolverRegistry.getArgumentResolver(method, i);
			variables = variablesFactory.merge(variables, argumentResolver.create(method, i));
		}

		return variables;
	}



}
