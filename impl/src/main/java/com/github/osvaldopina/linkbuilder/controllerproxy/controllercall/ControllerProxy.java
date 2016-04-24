package com.github.osvaldopina.linkbuilder.controllerproxy.controllercall;

import com.github.osvaldopina.linkbuilder.impl.LinkBuilderImpl;
import org.aopalliance.aop.Advice;
import org.springframework.aop.Advisor;
import org.springframework.aop.Pointcut;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.BeanUtils;

public class ControllerProxy {

    public static <T> T createProxy(Class<T> clazz, LinkBuilderImpl builder) {

        T controller = BeanUtils.instantiate(clazz);

        Pointcut pc = new ControllerPointcut();
        Advice advice = new ControllerAdvice(builder);
        Advisor advisor = new DefaultPointcutAdvisor(pc, advice);

        ProxyFactory pf = new ProxyFactory();
        pf.addAdvisor(advisor);
        pf.setTarget(controller);
        return (T) pf.getProxy();
    }

}
