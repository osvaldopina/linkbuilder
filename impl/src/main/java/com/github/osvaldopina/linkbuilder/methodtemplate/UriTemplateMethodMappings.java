package com.github.osvaldopina.linkbuilder.methodtemplate;

import com.damnhandy.uri.template.UriTemplate;
import com.github.osvaldopina.linkbuilder.LinkBuilderException;
import com.github.osvaldopina.linkbuilder.argumentresolver.ArgumentResolvers;
import com.github.osvaldopina.linkbuilder.utils.UriTemplateAugmenter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.annotation.PostConstruct;
import java.lang.reflect.Method;
import java.util.*;

@Controller
public class UriTemplateMethodMappings {

    private final Log logger = LogFactory.getLog(getClass());

    @Autowired
    private RequestMappingHandlerMapping handlerMapping;

    @Autowired
    private ArgumentResolvers argumentResolvers;

    private Map<Method, UriTemplate> methodTemplateMapping = new HashMap<Method, UriTemplate>();

    private UriTemplateAugmenter.Factory uriTemplateAugmentFactory = new UriTemplateAugmenter.Factory();

    private TemplateGeneratorIntrospector templateGeneratorIntrospector = new TemplateGeneratorIntrospector();

    private TemplateGenerator templateGenerator = new TemplateGenerator();

    @PostConstruct
    public void init() {

        Map<RequestMappingInfo, HandlerMethod> handlerMethods = handlerMapping.getHandlerMethods();

        for (Map.Entry<RequestMappingInfo, HandlerMethod> entry : handlerMethods.entrySet()) {
            Method method = entry.getValue().getMethod();
            if (templateGeneratorIntrospector.haveToGenerateTemplateFor(method)) {

                UriTemplate uriTemplate = templateGenerator.generate(method, argumentResolvers);

                logger.info("Registered for method:" + method + ", uri template:" + uriTemplate.getTemplate());
                methodTemplateMapping.put(method, uriTemplate);
            }
        }
    }

    public UriTemplate getTemplateForMethod(Method method) {
        UriTemplate template = methodTemplateMapping.get(method);

        if (template == null) {
            throw new LinkBuilderException("Could not find template for method " +
                    method +
                    ".\n Was this method annotated with @EnableSelfFromCurrentCall or @GenerateUriTemplateFor?");
        }
        else {
            return template;
        }
    }

    public UriTemplate createNewTemplateForMethod(String baseUri, Method method) {
        return UriTemplate.buildFromTemplate(baseUri + getTemplateForMethod(method).getTemplate()).build();
    }


}
