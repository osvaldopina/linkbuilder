package com.github.osvaldopina.linkbuilder.resoucemethod.springhateoas;

import com.github.osvaldopina.linkbuilder.resoucemethod.ResourceMethodRegistry;
import com.github.osvaldopina.linkbuilder.template.Template;
import com.github.osvaldopina.linkbuilder.utils.IntrospectionUtils;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Controller;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

class ResourceMethodRegistryFactory {

    public static final ResourceMethodRegistryFactory INSTANCE = new ResourceMethodRegistryFactory();

 //   private ApplicationContext applicationContext;


    ResourceMethodRegistryFactory() {

    }

    public List<Method> create(IntrospectionUtils introspectionUtils, ApplicationContext applicationContext) {
//        public List<Method> create(IntrospectionUtils introspectionUtils,
//                RequestMappingHandlerMapping handlerMapping) {

 //       Map<RequestMappingInfo, HandlerMethod> handlerMethods = handlerMapping.getHandlerMethods();

        List<Method> resourceMethods = new ArrayList<Method>();


        Map<String,Object> controllers = applicationContext.getBeansWithAnnotation(Controller.class);

//        for (Map.Entry<RequestMappingInfo, HandlerMethod> entry : handlerMethods.entrySet()) {
//            Method method = entry.getValue().getMethod();
//            if (introspectionUtils.haveToGenerateTemplateFor(method)) {
//                resourceMethods.add(method);
//            }
//        }
        for(Object controller: controllers.values()) {
            Class<?> cleanControllerClass = AopProxyUtils.ultimateTargetClass(controller);
            for(Method method: cleanControllerClass.getMethods()) {
                if (introspectionUtils.haveToGenerateTemplateFor(method)) {
                    resourceMethods.add(method);
                }
            }
        }

//        for (Map.Entry<RequestMappingInfo, HandlerMethod> entry : handlerMethods.entrySet()) {
//            Method method = entry.getValue().getMethod();
//            if (introspectionUtils.haveToGenerateTemplateFor(method)) {
//                resourceMethods.add(method);
//            }
//        }
        return resourceMethods;
    }

}
