package com.github.osvaldopina.linkbuilder.direct;

import com.github.osvaldopina.linkbuilder.annotation.Links;
import com.github.osvaldopina.linkbuilder.utils.TemplateBuilderInstrospectionUtils;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.lang.reflect.Method;

public class DirectLinkTargetBeanPostProcessor implements BeanPostProcessor, ApplicationContextAware {

    private TemplateBuilderInstrospectionUtils templateBuilderInstrospectionUtils = new TemplateBuilderInstrospectionUtils();

    private ApplicationContext applicationContext;


    public DirectLinkTargetBeanPostProcessor() {
    }

    protected DirectLinkTargetBeanPostProcessor(TemplateBuilderInstrospectionUtils templateBuilderInstrospectionUtils) {
        this.templateBuilderInstrospectionUtils = templateBuilderInstrospectionUtils;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (templateBuilderInstrospectionUtils.isRestController(bean)) {
            for (Method method : templateBuilderInstrospectionUtils.getAnnotatedMethods(bean, Links.class)) {
                ProxyFactory factory = new ProxyFactory();
                factory.addAdvice(new AddLinksToResourceMethodInterceptor(applicationContext));
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
