package com.github.osvaldopina.linkbuilder.fromcall.currentcallrecorder;

import com.github.osvaldopina.linkbuilder.utils.IntrospectionUtils;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.lang.reflect.Method;

public class CurrentCallBeanPostProcessor implements BeanPostProcessor, ApplicationContextAware {

    @Autowired
    private IntrospectionUtils introspectionUtils;

    private ApplicationContext applicationContext;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (introspectionUtils.isRestController(bean)) {
            for (Method method : introspectionUtils.getEnableSelfFromCurrentCallAnnotatedMethods(bean)) {
                ProxyFactory factory = new ProxyFactory();
                factory.addAdvice(new CurrentCallRecorderMethodInterceptor(applicationContext));
                factory.setTarget(bean);
                return factory.getProxy();
            }
        }
        return bean;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }


}
