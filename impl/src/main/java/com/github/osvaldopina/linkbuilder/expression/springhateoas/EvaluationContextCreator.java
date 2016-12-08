package com.github.osvaldopina.linkbuilder.expression.springhateoas;

import org.springframework.context.ApplicationContext;
import org.springframework.context.expression.BeanFactoryResolver;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.security.core.context.SecurityContextHolder;

public class EvaluationContextCreator {

	private SecurityExpressionHandler securityExpressionHandler = new SecurityExpressionHandler();

	private DummyFilterInvocationCreator dummyFilterInvocationCreator = new DummyFilterInvocationCreator();

	public EvaluationContext create(ApplicationContext applicationContext) {

		if (SecurityContextHolder.getContext().getAuthentication() == null) {
			StandardEvaluationContext standardEvaluationContext = new StandardEvaluationContext();
			standardEvaluationContext.setBeanResolver(new BeanFactoryResolver(applicationContext));
			return standardEvaluationContext;
		} else {
			return securityExpressionHandler.createEvalutationContext(
					applicationContext,
					SecurityContextHolder.getContext().getAuthentication(),
					dummyFilterInvocationCreator.create());
		}
	}
}
