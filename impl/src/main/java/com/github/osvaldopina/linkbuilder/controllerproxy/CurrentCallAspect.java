package com.github.osvaldopina.linkbuilder.controllerproxy;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * Created by deinf.osvaldo on 14/12/2015.
 */
@Aspect
@Component
public class CurrentCallAspect implements ApplicationContextAware {

    private final Log logger = LogFactory.getLog(getClass());
    private ApplicationContext applicationContext;


    @Before("@within(GenerateUriTemplateFor) || " +
            "@annotation(GenerateUriTemplateFor) ||" +
            "@within(GenerateUriTemplateFor) || " +
            "@annotation(GenerateUriTemplateFor)" )
    public void doAccessCheck(JoinPoint jp) {
        CurrentCall currentCall = applicationContext.getBean(CurrentCall.class);
        MethodSignature signature = (MethodSignature) jp.getSignature();
        Method method = signature.getMethod();
        currentCall.setMethod(method);
        currentCall.setParameters(jp.getArgs());
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
