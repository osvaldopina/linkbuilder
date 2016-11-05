package com.github.osvaldopina.linkbuilder.methodtemplate.linkcreator.impl;

import com.github.osvaldopina.linkbuilder.LinkBuilderException;
import com.github.osvaldopina.linkbuilder.annotation.EnableSelfFromCurrentCall;
import com.github.osvaldopina.linkbuilder.annotation.LinkTarget;
import com.github.osvaldopina.linkbuilder.expression.ExpressionExecutor;
import com.github.osvaldopina.linkbuilder.methodtemplate.linkcreator.AnnotatedLinkCreator;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceSupport;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;

public class SpringHateoasLinkCreator implements AnnotatedLinkCreator<Link> {

    private ExpressionExecutor expressionExecutor;

    public SpringHateoasLinkCreator(ExpressionExecutor expressionExecutor) {
        this.expressionExecutor = expressionExecutor;
    }

    @Override
    public Link createLink(Method method, Annotation annotation, String uri, Object payload, Object[] params) {
        if (annotation instanceof com.github.osvaldopina.linkbuilder.annotation.Link) {
            com.github.osvaldopina.linkbuilder.annotation.Link link =
                    (com.github.osvaldopina.linkbuilder.annotation.Link) annotation;


            if ("".equals(link.when().trim()) || expressionExecutor.isTrue(link.when(), payload, params)) {
                String rel = ((com.github.osvaldopina.linkbuilder.annotation.Link) annotation).relation();

//                if (rel == null || "".equals(rel)) {
//                    // refator to move to introspection utils
//                    rel = method.getAnnotation(LinkTarget.class).rel();
//                }

                if (rel == null || "".equals(rel)) {
                    throw new LinkBuilderException("Could not determine link relation.");
                }

                return new Link(uri, rel);
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
