package com.github.osvaldopina.linkbuilder.annotation.intercept.controller;

import com.github.osvaldopina.linkbuilder.annotation.linkcreator.LinkAnnotationCreator;
import com.github.osvaldopina.linkbuilder.annotation.linkcreator.LinkAnnotationCreatorRegistry;
import com.github.osvaldopina.linkbuilder.fromcall.MethodCall;
import com.github.osvaldopina.linkbuilder.fromcall.MethodCallFactory;
import org.easymock.EasyMockRule;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;

import java.lang.reflect.Method;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;



public class AnnotatedLinksMethodInterceptorTest extends EasyMockSupport {

    @Rule
    public EasyMockRule mocks = new EasyMockRule(this);

    @Mock
    MethodCall methodCall;

    @Mock
    private LinkAnnotationCreatorRegistry linkCreatorRegistry;

    @Mock
    private LinkAnnotationCreator linkAnnotationCreator;

    @Mock
    private MethodCallFactory methodCallFactory;

    Object returnValue = new Object();

    Method method = Object.class.getMethod("toString");

    Object[] args = {};

    Object target = new Object();

    @TestSubject
    AnnotatedLinksMethodInterceptor annotatedLinksMethodInterceptor =
            new AnnotatedLinksMethodInterceptor(null);


    public AnnotatedLinksMethodInterceptorTest() throws Exception {
    }

    @Test
    public void afterReturning_nullReturnValue() throws Throwable {

        replayAll();

        annotatedLinksMethodInterceptor.afterReturning(null, method, args, target);

        verifyAll();
    }

    @Test
    public void afterReturning_nonNullReturnVaalue() throws Throwable {

        expect(linkCreatorRegistry.get(method)).andReturn(linkAnnotationCreator);
        expect(methodCallFactory.create(method, args)).andReturn(methodCall);
        linkAnnotationCreator.createAndSetForMethodAnnotations(methodCall, returnValue);
        expectLastCall();

        replayAll();

        annotatedLinksMethodInterceptor.afterReturning(returnValue, method, args, target);

        verifyAll();
    }

}