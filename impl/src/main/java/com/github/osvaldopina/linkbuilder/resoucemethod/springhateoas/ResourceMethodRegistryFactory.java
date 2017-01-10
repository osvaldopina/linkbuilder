package com.github.osvaldopina.linkbuilder.resoucemethod.springhateoas;

import com.github.osvaldopina.linkbuilder.resoucemethod.ResourceMethodRegistry;
import com.github.osvaldopina.linkbuilder.template.Template;
import com.github.osvaldopina.linkbuilder.utils.IntrospectionUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

class ResourceMethodRegistryFactory {

    public static final ResourceMethodRegistryFactory INSTANCE = new ResourceMethodRegistryFactory();


    ResourceMethodRegistryFactory() {

    }

    public List<Method> create(IntrospectionUtils introspectionUtils,
                                         RequestMappingHandlerMapping handlerMapping) {
        Map<RequestMappingInfo, HandlerMethod> handlerMethods = handlerMapping.getHandlerMethods();

        List<Method> resourceMethods = new ArrayList<Method>();

        Template template;
        for (Map.Entry<RequestMappingInfo, HandlerMethod> entry : handlerMethods.entrySet()) {
            Method method = entry.getValue().getMethod();
            if (introspectionUtils.haveToGenerateTemplateFor(method)) {
                resourceMethods.add(method);
            }
        }
        return resourceMethods;
    }
}
