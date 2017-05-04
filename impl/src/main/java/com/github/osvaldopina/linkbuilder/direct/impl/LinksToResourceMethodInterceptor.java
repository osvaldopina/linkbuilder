package com.github.osvaldopina.linkbuilder.direct.impl;

import com.github.osvaldopina.linkbuilder.methodtemplate.linkcreator.AnnotatedLinkCreator;
import com.github.osvaldopina.linkbuilder.methodtemplate.urigenerator.AnnotatedMethodUriGenerator;
import com.github.osvaldopina.linkbuilder.methodtemplate.urigenerator.AnnotatedMethodUriGenerators;
import com.github.osvaldopina.linkbuilder.utils.IntrospectionUtils;
import org.springframework.aop.AfterReturningAdvice;
import org.springframework.http.ResponseEntity;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class LinksToResourceMethodInterceptor<T> implements AfterReturningAdvice {

    private AnnotatedLinkCreator<T> annotatedLinkCreator;

    private AnnotatedMethodUriGenerators annotatedMethodUriGenerators;

    private IntrospectionUtils introspectionUtils;

    public LinksToResourceMethodInterceptor(AnnotatedLinkCreator<T> annotatedLinkCreator,
                                            AnnotatedMethodUriGenerators annotatedMethodUriGenerators,
                                            IntrospectionUtils introspectionUtils) {
        this.annotatedLinkCreator = annotatedLinkCreator;
        this.annotatedMethodUriGenerators = annotatedMethodUriGenerators;
        this.introspectionUtils = introspectionUtils;
    }

    @Override
    public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {

        Object returnedResource = null;

        if (returnValue == null) {
            return;
        }
        else {
            returnedResource = returnValue;
        }


        if (returnedResource instanceof ResponseEntity) {
            returnedResource = ((ResponseEntity) returnedResource).getBody();

            if (returnedResource == null) {
                return;
            }
        }

        if (!introspectionUtils.isLinksAnnotatedMethod(method)) {
            return;
        }

        AnnotatedMethodUriGenerator annotatedMethodUriGenerator =
                annotatedMethodUriGenerators.getAnnotatedMethodUriGenerator(method);


        String uri;
        List<T> linkList = new ArrayList<T>();
        T link;
        for (Annotation linkAnnotation : annotatedMethodUriGenerator.getLinksAnnotation(method)) {
            uri = annotatedMethodUriGenerator.generate(method, linkAnnotation, returnedResource, args);
            link = annotatedLinkCreator.createLink(method, linkAnnotation, uri, returnedResource, args);
            if (link != null) {
                linkList.add(link);
            }
        }
        Annotation selfLinkAnnotion = annotatedMethodUriGenerator.getSelfLinkAnnotaiton(method);
        if (selfLinkAnnotion != null) {
            uri = annotatedMethodUriGenerator.generate(method, selfLinkAnnotion, returnedResource, args);
            if (uri != null) {
                linkList.add(annotatedLinkCreator.createLink(method, selfLinkAnnotion, uri, returnedResource, args));
            }
        }
        annotatedLinkCreator.setLinks(returnedResource, linkList);
    }


}
