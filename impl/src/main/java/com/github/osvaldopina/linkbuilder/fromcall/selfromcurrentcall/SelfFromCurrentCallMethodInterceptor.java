package com.github.osvaldopina.linkbuilder.fromcall.selfromcurrentcall;

import com.github.osvaldopina.linkbuilder.annotation.linkcreator.springhateoas.LinkCreatorForAnnotations;
import com.github.osvaldopina.linkbuilder.fromcall.MethodCall;
import com.github.osvaldopina.linkbuilder.fromcall.MethodCallFactory;
import com.github.osvaldopina.linkbuilder.urigeneration.link.methodcall.MethodCallUriGenerator;
import com.github.osvaldopina.linkbuilder.utils.IntrospectionUtils;
import org.springframework.aop.AfterReturningAdvice;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.context.ApplicationContext;
import org.springframework.hateoas.ResourceSupport;

import java.lang.reflect.Method;

public class SelfFromCurrentCallMethodInterceptor implements AfterReturningAdvice {

    private MethodCallFactory methodCallFactory = MethodCallFactory.INSTANCE;

    private LinkCreatorForAnnotations linkCreatorForAnnotations = LinkCreatorForAnnotations.INSTANCE;

    private MethodCallUriGenerator methodCallUriGenerator;

    private IntrospectionUtils introspectionUtils;

    public SelfFromCurrentCallMethodInterceptor(MethodCallUriGenerator methodCallUriGenerator,
												IntrospectionUtils introspectionUtils) {

        this.methodCallUriGenerator = methodCallUriGenerator;
        this.introspectionUtils = introspectionUtils;
    }

     @Override
    public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {

         if (returnValue != null) {
             linkCreatorForAnnotations.createAndSetSelfLinkIfNeeded(methodCallUriGenerator, introspectionUtils,
                     methodCallFactory.create(method, args), returnValue);
         }
    }

}
