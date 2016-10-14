package com.github.osvaldopina.linkbuilder.fromcall.controllercallrecorder;

import org.aopalliance.aop.Advice;
import org.springframework.aop.Advisor;
import org.springframework.aop.Pointcut;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.BeanUtils;

public class ControllerProxy {

    public static <T> T createProxy(Class<T> clazz, CallRecorder callRecorder) {

        T controller = BeanUtils.instantiate(clazz);

        Pointcut pc = new ControllerPointcut();
        Advice advice = new ControllerAdvice(callRecorder);
        Advisor advisor = new DefaultPointcutAdvisor(pc, advice);

        ProxyFactory pf = new ProxyFactory();
        pf.addAdvisor(advisor);
        pf.setTarget(controller);
        return (T) pf.getProxy();
    }

}
