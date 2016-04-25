package com.github.osvaldopina.linkbuilder.impl.security;

import org.easymock.*;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

import static org.junit.Assert.assertSame;

public class SecurityExpressionParserTest extends EasyMockSupport {

    @Rule
    public EasyMockRule mocks = new EasyMockRule(this);

    @Mock
    private ApplicationContext applicationContext;

    @Mock
    private SecurityExpressionHandler securityExpressionHandler;

    @Mock
    private Map<String,Expression> cache;

    @Mock
    private Expression parsedExpression;

    @TestSubject
    private SecurityExpressionParser securityExpressionParser = new SecurityExpressionParser();

    @Test
    public void getExpressionForNonCachedExpression() throws Exception {

        String expression = "security expression";

        EasyMock.expect(cache.get(expression)).andReturn(null);
        EasyMock.expect(securityExpressionHandler.parse(applicationContext, expression)).andReturn(parsedExpression);
        EasyMock.expect(cache.put(expression, parsedExpression)).andReturn(parsedExpression);

        replayAll();

        assertSame(parsedExpression, securityExpressionParser.parse(applicationContext, expression));

        verifyAll();

    }

    @Test
    public void getExpressionForCachedExpression() throws Exception {

        String expression = "security expression";

        EasyMock.expect(cache.get(expression)).andReturn(parsedExpression);

        replayAll();

        assertSame(parsedExpression, securityExpressionParser.parse(applicationContext, expression));

        verifyAll();

    }

}