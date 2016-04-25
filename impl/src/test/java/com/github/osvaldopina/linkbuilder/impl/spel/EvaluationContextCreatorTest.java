package com.github.osvaldopina.linkbuilder.impl.spel;

import org.easymock.*;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.expression.EvaluationContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.FilterInvocation;

import static org.junit.Assert.*;

public class EvaluationContextCreatorTest  extends EasyMockSupport {

    @Rule
    public EasyMockRule mocks = new EasyMockRule(this);

    @Mock
    private ApplicationContext applicationContext;

    @Mock
    private SecurityExpressionHandler securityExpressionHandler;

    @Mock
    private DummyFilterInvocationCreator dummyFilterInvocationCreator;

    @Mock
    private EvaluationContext evaluationContext;

    @Mock
    private Authentication authentication;

    @Mock
    private FilterInvocation filterInvocation;

    @TestSubject
    private EvaluationContextCreator evaluationContextCreator = new EvaluationContextCreator();

    @Test
    public void create() throws Exception {

        SecurityContextHolder.getContext().setAuthentication(authentication);
        EasyMock.expect(dummyFilterInvocationCreator.create()).andReturn(filterInvocation);
        EasyMock.expect(securityExpressionHandler.createEvalutationContext(
                applicationContext,
                authentication,
                filterInvocation)
        ).andReturn(evaluationContext);

        replayAll();
        assertSame(evaluationContext, evaluationContextCreator.create(applicationContext));
        verifyAll();

    }
}