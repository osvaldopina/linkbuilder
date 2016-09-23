package com.github.osvaldopina.linkbuilder.spel.impl;

import com.github.osvaldopina.linkbuilder.LinkBuilderException;
import com.github.osvaldopina.linkbuilder.spel.SpelExecutor;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.EvaluationException;
import org.springframework.expression.Expression;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.security.access.expression.ExpressionUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.Assert;

public class SpelExecutorImpl implements SpelExecutor, ApplicationContextAware {

    private EvaluationContextCreator evaluationContextCreator =
            new EvaluationContextCreator();

    private SecurityExpressionParser securityExpressionParser =
            new SecurityExpressionParser();

    private ApplicationContext applicationContext;

    @Override
    public boolean isTrue(String expression, Object payload, Object[] params) {
        Assert.notNull(SecurityContextHolder.getContext().getAuthentication(), "Authentication is null. " +
                "Is spel enabled?");

        EvaluationContext ctx = evaluationContextCreator.create(applicationContext);

        if (payload != null) {
            ctx.setVariable("payload", payload);
        }

        if (params != null) {
            ctx.setVariable("params", params);
        }

        Expression parsedExpression = securityExpressionParser.parse(applicationContext, expression);

        return ExpressionUtils.evaluateAsBoolean(parsedExpression, ctx);
    }

    @Override
    public Object getValue(String expression, Object payload, Object[] params) {
        Assert.notNull(SecurityContextHolder.getContext().getAuthentication(), "Authentication is null. " +
                "Is spel enabled?");

        EvaluationContext ctx = evaluationContextCreator.create(applicationContext);

        if (payload != null) {
            ctx.setVariable("payload", payload);
        }

        if (params != null) {
            ctx.setVariable("params", params);
        }

        Expression parsedExpression = securityExpressionParser.parse(applicationContext, expression);

        try {
            return parsedExpression.getValue(ctx);
        }
        catch (EvaluationException e) {
            throw new LinkBuilderException("Failed to evaluate expression '"
                    + parsedExpression.getExpressionString() + "'", e);
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
