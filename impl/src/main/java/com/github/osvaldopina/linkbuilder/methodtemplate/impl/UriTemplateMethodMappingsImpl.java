package com.github.osvaldopina.linkbuilder.methodtemplate.impl;

import com.damnhandy.uri.template.UriTemplate;
import com.github.osvaldopina.linkbuilder.argumentresolver.ArgumentResolvers;
import com.github.osvaldopina.linkbuilder.methodtemplate.UriTemplateMethodMappings;
import com.github.osvaldopina.linkbuilder.methodtemplate.templategenerator.MethodTemplateGenerator;
import com.github.osvaldopina.linkbuilder.methodtemplate.uridiscover.BaseUriDiscover;
import com.github.osvaldopina.linkbuilder.utils.IntrospectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.annotation.PostConstruct;
import java.lang.reflect.Method;
import java.util.Map;

public class UriTemplateMethodMappingsImpl implements UriTemplateMethodMappings {

    private final Log logger = LogFactory.getLog(getClass());

    @Autowired
    private RequestMappingHandlerMapping handlerMapping;

    @Autowired
    private ArgumentResolvers argumentResolvers;

    @Autowired
    private BaseUriDiscover baseUriDiscover;

    @Autowired
    private MethodTemplateGenerator methodTemplateGenerator;

    @Autowired
    private IntrospectionUtils introspectionUtils;

    private UriTemplateMethodRegister uriTemplateMethodRegister;

    @PostConstruct
    private void init() {

        uriTemplateMethodRegister = new UriTemplateMethodRegister(introspectionUtils);

        Map<RequestMappingInfo, HandlerMethod> handlerMethods = handlerMapping.getHandlerMethods();

        for (Map.Entry<RequestMappingInfo, HandlerMethod> entry : handlerMethods.entrySet()) {
            Method method = entry.getValue().getMethod();
            if (introspectionUtils.haveToGenerateTemplateFor(method)) {

               UriTemplate uriTemplate = methodTemplateGenerator.generate(method);

               uriTemplateMethodRegister.register(method, uriTemplate);

            }
        }
    }

    public UriTemplate createTemplateForMethod(Method method) {
        return UriTemplate.buildFromTemplate(baseUriDiscover.getBaseUri() +
                uriTemplateMethodRegister.get(method).getTemplate()).build();
    }

    @Override
    public UriTemplate createTemplateForLinkTarget(Class<?> controller, String target) {
        return UriTemplate.buildFromTemplate(baseUriDiscover.getBaseUri() +
                uriTemplateMethodRegister.get(controller, target).getTemplate()).build();
    }

}
