package com.github.osvaldopina.linkbuilder.methodtemplate.impl;

import com.damnhandy.uri.template.UriTemplate;
import com.github.osvaldopina.linkbuilder.argumentresolver.ArgumentResolvers;
import com.github.osvaldopina.linkbuilder.methodtemplate.TemplateGenerator;
import com.github.osvaldopina.linkbuilder.methodtemplate.UriTemplateMethodMappings;
import com.github.osvaldopina.linkbuilder.methodtemplate.uridiscover.BaseUriDiscover;
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
    private TemplateGenerator templateGenerator;

    private TemplateGeneratorIntrospector templateGeneratorIntrospector = new TemplateGeneratorIntrospector();

    private UriTemplateMethodRegister uriTemplateMethodRegister = new UriTemplateMethodRegister();

    @PostConstruct
    private void init() {

        Map<RequestMappingInfo, HandlerMethod> handlerMethods = handlerMapping.getHandlerMethods();

        for (Map.Entry<RequestMappingInfo, HandlerMethod> entry : handlerMethods.entrySet()) {
            Method method = entry.getValue().getMethod();
            if (templateGeneratorIntrospector.haveToGenerateTemplateFor(method)) {

               UriTemplate uriTemplate = templateGenerator.generate(method);

               uriTemplateMethodRegister.register(method, uriTemplate);

            }
        }
    }

    public UriTemplate createNewTemplateForMethod( Method method) {
        return UriTemplate.buildFromTemplate(baseUriDiscover.getBaseUri() +
                uriTemplateMethodRegister.get(method).getTemplate()).build();
    }

    @Override
    public UriTemplate createNewTemplateForLinkTarget(Class<?> controller, String target) {
        return UriTemplate.buildFromTemplate(baseUriDiscover.getBaseUri() +
                uriTemplateMethodRegister.get(controller, target).getTemplate()).build();
    }

}