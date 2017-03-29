package com.github.osvaldopina.linkbuilder.expression.springhateoas.bare;

import com.github.osvaldopina.linkbuilder.expression.springhateoas.impl.BaseExpressionHandler;
import org.springframework.context.ApplicationContext;
import org.springframework.context.expression.BeanFactoryResolver;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

public class BareContextExpressionHandler extends BaseExpressionHandler {

	private ExpressionParser expressionParser = new SpelExpressionParser();

	public BareContextExpressionHandler() {

	}

	@Override
	public EvaluationContext createEvalutationContext(ApplicationContext applicationContext) {
		StandardEvaluationContext standardEvaluationContext = new StandardEvaluationContext();
		standardEvaluationContext.setBeanResolver(new BeanFactoryResolver(applicationContext));
		return standardEvaluationContext;
	}

	@Override
	public Expression parse(ApplicationContext applicationContext, String expression) {
		return expressionParser.parseExpression(expression);
	}

	@Override
	public boolean isValid(ApplicationContext applicationContext) {
		return true;
	}

	@Override
	public int getPriority() {
		return Integer.MAX_VALUE;
	}

}
