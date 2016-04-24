package com.github.osvaldopina.linkbuilder.impl.spel;

import org.springframework.context.ApplicationContext;
import org.springframework.expression.Expression;

import java.util.HashMap;
import java.util.Map;

public class SecurityExpressionParser {

    private Map<String,Expression> expressionCache = new HashMap<String, Expression>();

    private SecurityExpressionHandler securityExpressionHandler =
            new SecurityExpressionHandler();

    public Expression parse(ApplicationContext applicationContext, String expression) {

        Expression parsedExpression = expressionCache.get(expression);

        if (parsedExpression == null) {
            parsedExpression = securityExpressionHandler.parse(applicationContext, expression);
            expressionCache.put(expression, parsedExpression);
        }

        return parsedExpression;
    }

}
