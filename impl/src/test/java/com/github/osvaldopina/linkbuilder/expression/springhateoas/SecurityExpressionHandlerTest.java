package com.github.osvaldopina.linkbuilder.expression.springhateoas;

import org.easymock.*;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.TypedValue;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.access.expression.WebSecurityExpressionRoot;

import javax.servlet.http.HttpServletRequest;

import static org.hamcrest.Matchers.*;
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

        assertThat(evaluationContext.getRootObject(), is(notNullValue()));
        assertEquals(TypedValue.class, evaluationContext.getRootObject().getClass());
        assertThat(evaluationContext.getRootObject().getClass(), is(typeCompatibleWith(TypedValue.class)));
        assertThat(((TypedValue) evaluationContext.getRootObject()).getValue().getClass(), is(typeCompatibleWith(WebSecurityExpressionRoot.class)));

        verifyAll();

    }

    @Test
    public void parse() throws Exception {
        String expression = "expression expression";

        EasyMock.expect(defaultWebSecurityExpressionHandlerRecover.recover(applicationContext)).andReturn(defaultWebSecurityExpressionHandler);
        defaultWebSecurityExpressionHandler.setExpressionParser(expressionParser);
        EasyMock.expect(expressionParser.parseExpression(expression)).andReturn(parsedExpression);

        replayAll();

        assertThat(securityExpressionHandler.parse(applicationContext,expression), is(sameInstance(parsedExpression)));

        verifyAll();

    }
}