package com.github.osvaldopina.linkbuilder.impl.security;

import com.github.osvaldopina.linkbuilder.LinkBuilderException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;

public class SecurityExpressionHandler {

    private DefaultWebSecurityExpressionHandlerRecover defaultWebSecurityExpressionHandlerRecover =
            new DefaultWebSecurityExpressionHandlerRecover();

    public EvaluationContext createEvalutationContext(ApplicationContext applicationContext,
            Authentication authentication, FilterInvocation filterInvocation) {

        DefaultWebSecurityExpressionHandler handler = defaultWebSecurityExpressionHandlerRecover.
                recover(applicationContext);

        return handler.createEvaluationContext(authentication, filterInvocation);
    }

    public Expression parse(ApplicationContext applicationContext, String expression) {
        DefaultWebSecurityExpressionHandler handler = defaultWebSecurityExpressionHandlerRecover.
                recover(applicationContext);

        return handler.getExpressionParser().parseExpression(expression);
    }

}