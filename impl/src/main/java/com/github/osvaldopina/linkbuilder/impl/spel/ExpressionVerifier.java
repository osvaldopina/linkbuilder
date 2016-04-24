package com.github.osvaldopina.linkbuilder.impl.spel;

import org.springframework.context.ApplicationContext;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.security.access.expression.ExpressionUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.Assert;

public class ExpressionVerifier {

    private EvaluationContextCreator evaluationContextCreator =
            new EvaluationContextCreator();

    private SecurityExpressionParser securityExpressionParser =
            new SecurityExpressionParser();

    public boolean isTrue(String expression, ApplicationContext applicationContext, Object payload) {

        Assert.notNull(SecurityContextHolder.getContext().getAuthentication(), "Authentication is null. " +
                "Is spel enabled?");

        EvaluationContext ctx = evaluationContextCreator.create(applicationContext);

        if (payload != null) {
            ctx.setVariable("payload", payload);
        }

        Expression parsedExpression = securityExpressionParser.parse(applicationContext, expression);

        return ExpressionUtils.evaluateAsBoolean(parsedExpression, ctx);
    }
}
