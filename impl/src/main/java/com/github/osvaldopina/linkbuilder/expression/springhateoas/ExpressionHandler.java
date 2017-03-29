package com.github.osvaldopina.linkbuilder.expression.springhateoas;

import org.springframework.context.ApplicationContext;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;

public interface ExpressionHandler extends Comparable<ExpressionHandler> {


	EvaluationContext createEvalutationContext(ApplicationContext applicationContext);

	Expression parse(ApplicationContext applicationContext, String expression);

    boolean isValid(ApplicationContext applicationContext);

	int getPriority();
}
