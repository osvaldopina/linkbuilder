package com.github.osvaldopina.linkbuilder.expression.springhateoas;

import com.github.osvaldopina.linkbuilder.LinkBuilderException;
import com.github.osvaldopina.linkbuilder.expression.ExpressionExecutor;
import org.springframework.context.ApplicationContext;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.EvaluationException;
import org.springframework.expression.Expression;



public class ExpressionExecutorImpl implements ExpressionExecutor {

    private ExpressionHandlerDiscover expressionHandlerDiscover;

    private ApplicationContext applicationContext;

    public ExpressionExecutorImpl(ExpressionHandlerDiscover expressionHandlerDiscover,
                                  ApplicationContext applicationContext) {
        this.expressionHandlerDiscover = expressionHandlerDiscover;
        this.applicationContext = applicationContext;
    }

    @Override
    public boolean isTrue(String expression, Object resource, Object[] params) {

        return (Boolean) getValue(expression, resource, params);
    }

    @Override
    public Object getValue(String expression, Object resource, Object[] params) {

        EvaluationContext ctx = createEvaluationContext(resource, params);

        Expression parsedExpression = expressionHandlerDiscover.getExpressionHandler(applicationContext).
                parse(applicationContext, expression);

        try {
            return parsedExpression.getValue(ctx);
        }
        catch (EvaluationException e) {
            throw new LinkBuilderException("Failed to evaluate expression '"
                    + parsedExpression.getExpressionString() + "'", e);
        }
    }

    private EvaluationContext createEvaluationContext(Object resource, Object[] params) {
        EvaluationContext ctx =  expressionHandlerDiscover.getExpressionHandler(applicationContext).
                createEvalutationContext(applicationContext);

        if (resource != null) {
            ctx.setVariable("resource", resource);
        }

        if (params != null) {
            ctx.setVariable("variables", params);
        }
        return ctx;
    }

}
