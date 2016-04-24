package com.github.osvaldopina.linkbuilder.impl.spel;

import org.springframework.context.ApplicationContext;
import org.springframework.expression.EvaluationContext;
import org.springframework.security.core.context.SecurityContextHolder;

public class EvaluationContextCreator {

    private SecurityExpressionHandler securityExpressionHandler = new SecurityExpressionHandler();

    private DummyFilterInvocationCreator dummyFilterInvocationCreator = new DummyFilterInvocationCreator();

    public EvaluationContext create(ApplicationContext applicationContext) {

        return securityExpressionHandler.createEvalutationContext(
                applicationContext,
                SecurityContextHolder.getContext().getAuthentication(),
                dummyFilterInvocationCreator.create());
    }
}
