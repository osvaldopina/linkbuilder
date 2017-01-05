package com.github.osvaldopina.linkbuilder.resoucemethod.springhateoas;

import java.util.Collections;

import com.github.osvaldopina.linkbuilder.resoucemethod.ResourceMethodRegistry;
import com.github.osvaldopina.linkbuilder.resoucemethod.ResourceMethodRegistryFactory;
import com.github.osvaldopina.linkbuilder.utils.IntrospectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

public class ResourceMethodRegistryFactoryImpl implements ResourceMethodRegistryFactory {

//    @Autowired
//    @Lazy
//    private RequestMappingHandlerMapping handlerMapping;

    private IntrospectionUtils introspectionUtils;

    public ResourceMethodRegistryFactoryImpl(IntrospectionUtils introspectionUtils) {
//    public ResourceMethodRegistryFactoryImpl(RequestMappingHandlerMapping handlerMapping, IntrospectionUtils introspectionUtils) {
 //       this.handlerMapping = handlerMapping;
        this.introspectionUtils = introspectionUtils;
    }

    // TODO voltar ao normal
    @Override
    public ResourceMethodRegistry create() {
//        Map<RequestMappingInfo, HandlerMethod> handlerMethods = handlerMapping.getHandlerMethods();
//
//        List<Method> resourceMethods = new ArrayList<Method>();
//
//        Template template;
//        for (Map.Entry<RequestMappingInfo, HandlerMethod> entry : handlerMethods.entrySet()) {
//            Method method = entry.getValue().getMethod();
//            if (introspectionUtils.haveToGenerateTemplateFor(method)) {
//                resourceMethods.add(method);
//            }
//        }
//        return new ResourceMethodRegistryImpl(resourceMethods);
 //       return new ResourceMethodRegistryImpl(Collections.EMPTY_LIST);
        throw new IllegalStateException("não deve ser chamado!!!!");
    }
}
