package com.github.osvaldopina.linkbuilder.expression.springhateoas;

import com.github.osvaldopina.linkbuilder.LinkBuilderException;
import org.easymock.EasyMock;
import org.easymock.EasyMockRule;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class DefaultWebSecurityExpressionHandlerRecoverTest extends EasyMockSupport {

    @Rule
    public EasyMockRule mocks = new EasyMockRule(this);

    @Mock
    private ApplicationContext applicationContext;

    @Mock
    private DefaultWebSecurityExpressionHandler defaultWebSecurityExpressionHandler;

    @Mock
    private DefaultWebSecurityExpressionHandlerRecover defaultWebSecurityExpressionHandlerRecover;

    @Test
    public void recover() throws Exception {

        EasyMock.expect(applicationContext.getBean(DefaultWebSecurityExpressionHandler.class)).
                andReturn(defaultWebSecurityExpressionHandler);

        defaultWebSecurityExpressionHandlerRecover = new DefaultWebSecurityExpressionHandlerRecover();

        replayAll();

        assertThat(defaultWebSecurityExpressionHandlerRecover.recover(applicationContext),
                is(sameInstance(defaultWebSecurityExpressionHandler)));

        verifyAll();
    }

    @Test
    public void recoverDefaultWebSecurityExpressionHandlerNotFound() throws Exception {

        Exception noSuchBeanDefinition = new NoSuchBeanDefinitionException("Not Found!");
        EasyMock.expect(applicationContext.getBean(DefaultWebSecurityExpressionHandler.class)).
                andThrow(noSuchBeanDefinition);

        defaultWebSecurityExpressionHandlerRecover = new DefaultWebSecurityExpressionHandlerRecover();

        replayAll();

        try {
            defaultWebSecurityExpressionHandlerRecover.recover(applicationContext);
        }
        catch (LinkBuilderException e) {
            assertEquals("A expression expression was configured but a instance of " +
                    DefaultWebSecurityExpressionHandler.class +
                    " could not be found because " + noSuchBeanDefinition + ". Is expression configured?", e.getMessage());
        }

        verifyAll();
    }

}