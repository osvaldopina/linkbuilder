package com.github.osvaldopina.linkbuilder.direct.impl;

import com.github.osvaldopina.linkbuilder.methodtemplate.linkcreator.AnnotatedLinkCreator;
import com.github.osvaldopina.linkbuilder.methodtemplate.urigenerator.AnnotatedMethodUriGenerator;
import com.github.osvaldopina.linkbuilder.methodtemplate.urigenerator.AnnotatedMethodUriGenerators;
import org.springframework.aop.AfterReturningAdvice;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class LinksToResourceMethodInterceptor<T> implements AfterReturningAdvice {

    private AnnotatedLinkCreator<T> annotatedLinkCreator;

    private AnnotatedMethodUriGenerators annotatedMethodUriGenerators;

    public LinksToResourceMethodInterceptor(AnnotatedLinkCreator<T> annotatedLinkCreator, AnnotatedMethodUriGenerators annotatedMethodUriGenerators) {
        this.annotatedLinkCreator = annotatedLinkCreator;
        this.annotatedMethodUriGenerators = annotatedMethodUriGenerators;
    }

    @Override
    public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {
        if (returnValue != null) {

            AnnotatedMethodUriGenerator annotatedMethodUriGenerator =
                    annotatedMethodUriGenerators.getAnnotatedMethodUriGenerator(method);


            String uri;
            List<T> linkList = new ArrayList<T>();
            T link;
            for(Annotation linkAnnotation : annotatedMethodUriGenerator.getLinksAnnotation(method)) {
                uri = annotatedMethodUriGenerator.generate(method, linkAnnotation,returnValue, args);
                link = annotatedLinkCreator.createLink(linkAnnotation, uri, returnValue, args);
                if (link != null) {
                    linkList.add(link);
                }
            }
            Annotation selfLinkAnnotion = annotatedMethodUriGenerator.getSelfLinkAnnotaiton(method);
            uri = annotatedMethodUriGenerator.generate(method, selfLinkAnnotion, returnValue, args);
            if (uri != null) {
                linkList.add(annotatedLinkCreator.createLink(selfLinkAnnotion, uri, returnValue, args));
            }
            annotatedLinkCreator.setLinks(returnValue, linkList);

        }
    }


}
