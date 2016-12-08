package com.github.osvaldopina.linkbuilder.expression.springhateoas;

import org.easymock.*;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class ExpressionExecutorImplTest extends EasyMockSupport {

    @Rule
    public EasyMockRule mocks = new EasyMockRule(this);

    @Mock
    private ApplicationContext applicationContext;

    @Mock
    private SecurityExpressionParser securityExpressionParser;

    @Mock
    private Expression parsedExpression;

    @Mock
    private EvaluationContextCreator evaluationContextCreator;

    @Mock
    private EvaluationContext evaluationContext;

    @Mock
    private Authentication authentication;

    @TestSubject
    ExpressionExecutorImpl spelExecutor = new ExpressionExecutorImpl();


    @Before
    public void setUp() {
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }


    @Test
    public void isTrueWithoutPayload() throws Exception {
        String expression = "expression expression";

        EasyMock.expect(evaluationContextCreator.create(applicationContext)).andReturn(evaluationContext);
        EasyMock.expect(securityExpressionParser.parse(applicationContext, expression)).andReturn(parsedExpression);
        EasyMock.expect(parsedExpression.getValue(evaluationContext, Boolean.class)).andReturn(Boolean.TRUE);

        replayAll();

        assertThat(spelExecutor.isTrue(expression, null, null), is(true));

        verifyAll();

    }

    @Test
    public void isTrueWithPayload() throws Exception {
        String expression = "expression expression";
        Object payload = new Object();
        Object[] parameters = new Object[] {};

        EasyMock.expect(evaluationContextCreator.create(applicationContext)).andReturn(evaluationContext);
        evaluationContext.setVariable("payload", payload);
        EasyMock.expectLastCall();
        evaluationContext.setVariable("params", parameters);
        EasyMock.expectLastCall();
        EasyMock.expect(securityExpressionParser.parse(applicationContext, expression)).andReturn(parsedExpression);
        EasyMock.expect(parsedExpression.getValue(evaluationContext, Boolean.class)).andReturn(Boolean.TRUE);

        replayAll();

        assertThat(spelExecutor.isTrue(expression, payload, parameters), is(true));

        verifyAll();

    }

}