package com.github.osvaldopina.linkbuilder.fromcall.currentcallrecorder;

import com.github.osvaldopina.linkbuilder.annotation.EnableSelfFromCurrentCall;
import com.github.osvaldopina.linkbuilder.utils.TemplateBuilderInstrospectionUtils;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.lang.reflect.Method;

public class CurrentCallBeanPostProcessor implements BeanPostProcessor, ApplicationContextAware {

    private TemplateBuilderInstrospectionUtils templateBuilderInstrospectionUtils = new TemplateBuilderInstrospectionUtils();

    private ApplicationContext applicationContext;


    public CurrentCallBeanPostProcessor() {
    }

    protected CurrentCallBeanPostProcessor(TemplateBuilderInstrospectionUtils templateBuilderInstrospectionUtils) {
        this.templateBuilderInstrospectionUtils = templateBuilderInstrospectionUtils;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (templateBuilderInstrospectionUtils.isRestController(bean)) {
            for (Method method : templateBuilderInstrospectionUtils.
                    getAnnotatedMethods(bean, EnableSelfFromCurrentCall.class)) {
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
