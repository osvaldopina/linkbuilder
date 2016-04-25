package com.github.osvaldopina.linkbuilder.impl.security;

import org.easymock.*;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.expression.BeanFactoryResolver;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.TypedValue;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.security.access.expression.SecurityExpressionOperations;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.access.expression.WebSecurityExpressionRoot;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

import static org.junit.Assert.*;

public class SecurityExpressionHandlerTest extends EasyMockSupport {

    @Rule
    public EasyMockRule mocks = new EasyMockRule(this);

    @Mock
    private ApplicationContext applicationContext;

    @Mock
    private Expression parsedExpression;

    @Mock
    private DefaultWebSecurityExpressionHandlerRecover defaultWebSecurityExpressionHandlerRecover;

    @Mock
    private ExpressionParser expressionParser;

    @Mock
    private Authentication authentication;

    @Mock
    private FilterInvocation filterInvocation;

    @Mock
    private HttpServletRequest request;

    private DefaultWebSecurityExpressionHandler defaultWebSecurityExpressionHandler =
            new DefaultWebSecurityExpressionHandler();

    @TestSubject
    private SecurityExpressionHandler securityExpressionHandler = new SecurityExpressionHandler();

    @Test
    public void createEvalutationContext() throws Exception {


        EasyMock.expect(defaultWebSecurityExpressionHandlerRecover.recover(applicationContext)).
                andReturn(defaultWebSecurityExpressionHandler);
        EasyMock.expect(filterInvocation.getRequest()).andReturn(request);

        replayAll();

        EvaluationContext evaluationContext = securityExpressionHandler.createEvalutationContext(
                applicationContext,
                authentication,
                filterInvocation);

        assertNotNull(evaluationContext.getRootObject());
        assertEquals(TypedValue.class, evaluationContext.getRootObject().getClass());
        assertEquals(WebSecurityExpressionRoot.class,  ((TypedValue) evaluationContext.getRootObject()).getValue().getClass());

        verifyAll();

    }

    @Test
    public void parse() throws Exception {
        String expression = "security expression";

        EasyMock.expect(defaultWebSecurityExpressionHandlerRecover.recover(applicationContext)).andReturn(defaultWebSecurityExpressionHandler);
        defaultWebSecurityExpressionHandler.setExpressionParser(expressionParser);
        EasyMock.expect(expressionParser.parseExpression(expression)).andReturn(parsedExpression);

        replayAll();

        assertSame(parsedExpression, securityExpressionHandler.parse(applicationContext, expression));

        verifyAll();

    }
}