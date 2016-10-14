package com.github.osvaldopina.linkbuilder.methodtemplate.linkcreator.impl;

import com.github.osvaldopina.linkbuilder.LinkBuilderException;
import com.github.osvaldopina.linkbuilder.annotation.EnableSelfFromCurrentCall;
import com.github.osvaldopina.linkbuilder.expression.ExpressionExecutor;
import com.github.osvaldopina.linkbuilder.methodtemplate.linkcreator.AnnotatedLinkCreator;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceSupport;

import java.lang.annotation.Annotation;
import java.util.List;

public class SpringHateoasLinkCreator implements AnnotatedLinkCreator<Link> {

    private ExpressionExecutor expressionExecutor;

    public SpringHateoasLinkCreator(ExpressionExecutor expressionExecutor) {
        this.expressionExecutor = expressionExecutor;
    }

    @Override
    public Link createLink(Annotation annotation, String uri, Object payload, Object[] params) {
        if (annotation instanceof com.github.osvaldopina.linkbuilder.annotation.Link) {
            com.github.osvaldopina.linkbuilder.annotation.Link link =
                    (com.github.osvaldopina.linkbuilder.annotation.Link) annotation;


            if ("".equals(link.when().trim()) || expressionExecutor.isTrue(link.when(), payload, params)) {
                return new Link(uri, ((com.github.osvaldopina.linkbuilder.annotation.Link) annotation).relation());
            } else {
                return null;
            }
        } else if (annotation instanceof EnableSelfFromCurrentCall) {
            return new Link(uri, "self");
        } else {
            throw new LinkBuilderException(
                    "The annotation must either a " +
                            com.github.osvaldopina.linkbuilder.annotation.Link.class.getName()
                            + " or a " + EnableSelfFromCurrentCall.class.getName());
        }
    }

    @Override
    public void setLinks(Object returnValue, List<Link> linkList) {
        if (!(returnValue instanceof ResourceSupport)) {
            throw new LinkBuilderException("The function must return a instance of " + Resource.class.getName());
        }
        ((ResourceSupport) returnValue).add(linkList);
    }


}
