package com.github.osvaldopina.linkbuilder.direct;

import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.lang.reflect.Method;

public class DirectLinkTargetBeanPostProcessor implements BeanPostProcessor , ApplicationContextAware {

    private DirectLinkReflectionUtils directLinkReflectionUtils = new DirectLinkReflectionUtils();

    private ApplicationContext applicationContext;


    protected DirectLinkTargetBeanPostProcessor(DirectLinkReflectionUtils directLinkReflectionUtils) {
        this.directLinkReflectionUtils = directLinkReflectionUtils;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (directLinkReflectionUtils.isRestController(bean)) {
            for(Method method: directLinkReflectionUtils.getLinkAnnotatedMethods(bean)) {
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
