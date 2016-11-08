package com.github.osvaldopina.linkbuilder.methodtemplate.urigenerator.impl;

import com.damnhandy.uri.template.UriTemplate;
import com.github.osvaldopina.linkbuilder.LinkBuilderException;
import com.github.osvaldopina.linkbuilder.annotation.EnableSelfFromCurrentCall;
import com.github.osvaldopina.linkbuilder.annotation.Link;
import com.github.osvaldopina.linkbuilder.annotation.Links;
import com.github.osvaldopina.linkbuilder.annotation.Param;
import com.github.osvaldopina.linkbuilder.expression.ExpressionExecutor;
import com.github.osvaldopina.linkbuilder.fromcall.currentcallrecorder.CurrentCallLocator;
import com.github.osvaldopina.linkbuilder.methodtemplate.MethodCall;
import com.github.osvaldopina.linkbuilder.methodtemplate.UriTemplateMethodMappings;
import com.github.osvaldopina.linkbuilder.methodtemplate.urigenerator.AnnotatedMethodUriGenerator;
import com.github.osvaldopina.linkbuilder.methodtemplate.urigenerator.MethodCallUriGenerator;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceSupport;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SpringLinkAnnotatedMethodUriGeneratorImpl implements AnnotatedMethodUriGenerator, ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public boolean isAnnotated(Method method) {
        return AnnotationUtils.findAnnotation(method, Links.class) != null ||
                AnnotationUtils.findAnnotation(method, EnableSelfFromCurrentCall.class) != null;
    }

    @Override
    public List<? extends Annotation> getLinksAnnotation(Method method) {

        Links links = AnnotationUtils.findAnnotation(method, Links.class);

        if (links == null) {
            return Collections.emptyList();
        }
        else {
            return Arrays.asList(links.value());
        }
    }

    @Override
    public Annotation getSelfLinkAnnotaiton(Method method) {
        return AnnotationUtils.findAnnotation(method, EnableSelfFromCurrentCall.class);
    }

    @Override
    public String generate(Method method, Annotation annotation, Object payLoad, Object[] params) {
        if (!(payLoad instanceof ResourceSupport)) {
            throw new LinkBuilderException(
                    "The payload must be a " + Resource.class.getName() + " but it was " + payLoad.getClass().getName()
            );
        }
        if (annotation instanceof Link) {
            return getLink(method, (Link) annotation, payLoad, params);
        } else if (annotation instanceof EnableSelfFromCurrentCall) {
            return selfLink((EnableSelfFromCurrentCall) annotation);
        } else {
            throw new LinkBuilderException(
                    "The annotation must be a " + Link.class.getName()
                            + " or " + EnableSelfFromCurrentCall.class.getName() + " but it was "
                            + annotation.getClass().getName()
            );
        }

    }

    private String selfLink(EnableSelfFromCurrentCall annotation) {
        CurrentCallLocator currentCallLocator = applicationContext.getBean(CurrentCallLocator.class);
        MethodCallUriGenerator methodCallUriGenerator = applicationContext.getBean(MethodCallUriGenerator.class);

        MethodCall methodCall = currentCallLocator.getCurrentCall();
        return methodCallUriGenerator.generate(methodCall, false);
    }

    private String getLink(Method method, Link link, Object payLoad, Object[] params) {

        UriTemplateMethodMappings uriTemplateMethodMappingsImpl =
                applicationContext.getBean(UriTemplateMethodMappings.class);

        ExpressionExecutor expressionExecutor = applicationContext.getBean(ExpressionExecutor.class);

        UriTemplate template = uriTemplateMethodMappingsImpl.createTemplateForLinkTarget(
                link.destination(),
                link.rel()
        );

        for (Param param : link.params()) {
            if (!templateHasVariable(template, param.name())) {
                throw new LinkBuilderException(
                        "Could not find variable " + param.name() + " in template " + template.getTemplate() +
                                " in method " + method
                );
            }
            template.set(param.name(), expressionExecutor.getValue(param.value(), payLoad, params));
        }

        return link.templated() ? template.expandPartial() : template.expand();
    }

    private boolean templateHasVariable(UriTemplate template, String name) {
        return Arrays.asList(template.getVariables()).contains(name);
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
