package com.github.osvaldopina.linkbuilder.methodtemplate.linkcreator;

import org.springframework.hateoas.Link;

import java.lang.annotation.Annotation;
import java.util.List;

public interface AnnotatedLinkCreator<T> {

    T createLink(Annotation annotation, String uri, Object payload, Object[] params);

    void setLinks(Object returnValue, List<T> linkList);
}

