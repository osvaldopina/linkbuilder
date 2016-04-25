package com.github.osvaldopina.linkbuilder.impl.security;

import org.easymock.*;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;

import static org.junit.Assert.*;

public class ExpressionVerifierTest extends EasyMockSupport {

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

    @TestSubject
    ExpressionVerifier expressionVerifier = new ExpressionVerifier();

    @Test
    public void isTrueWithoutPayload() throws Exception {
        String expression = "security expression";

        EasyMock.expect(evaluationContextCreator.create(applicationContext)).andReturn(evaluationContext);
        EasyMock.expect(securityExpressionParser.parse(applicationContext, expression)).andReturn(parsedExpression);
        EasyMock.expect(parsedExpression.getValue(evaluationContext, Boolean.class)).andReturn(Boolean.TRUE);

        replayAll();

        assertTrue(expressionVerifier.isTrue(expression, applicationContext, null));

        verifyAll();

    }

    @Test
    public void isTrueWithPayload() throws Exception {
        String expression = "security expression";
        Object payload = new Object();

        EasyMock.expect(evaluationContextCreator.create(applicationContext)).andReturn(evaluationContext);
        evaluationContext.setVariable("payload", payload);
        EasyMock.expectLastCall();
        EasyMock.expect(securityExpressionParser.parse(applicationContext, expression)).andReturn(parsedExpression);
        EasyMock.expect(parsedExpression.getValue(evaluationContext, Boolean.class)).andReturn(Boolean.TRUE);

        replayAll();

        assertTrue(expressionVerifier.isTrue(expression, applicationContext, payload));

        verifyAll();

    }

}