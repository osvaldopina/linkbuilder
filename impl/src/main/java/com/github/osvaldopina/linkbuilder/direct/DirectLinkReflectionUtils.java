package com.github.osvaldopina.linkbuilder.direct;

import com.github.osvaldopina.linkbuilder.LinkBuilderException;
import com.github.osvaldopina.linkbuilder.annotation.Link;
import com.github.osvaldopina.linkbuilder.annotation.LinkTarget;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class DirectLinkReflectionUtils {

    public boolean isRestController(Object bean) {
        return AnnotationUtils.findAnnotation(bean.getClass(), RestController.class) != null;
    }

    public Collection<Method> getLinkAnnotatedMethods(Object bean) {
        List<Method> linkAnnotatedMethods = new ArrayList<Method>();
        for (Method method : bean.getClass().getMethods()) {
            if (AnnotationUtils.findAnnotation(method, Link.class) != null) {
                checkReturnResource(method);
                linkAnnotatedMethods.add(method);
            }
        }
        return Collections.unmodifiableList(linkAnnotatedMethods);
    }


    public void checkReturnResource(Method method) {
        if (!ResourceSupport.class.isAssignableFrom(method.getReturnType())) {
            throw new LinkBuilderException("Method " + method + " is not valid for @Link because it do not return " +
                    Resource.class);
        }
    }

    public boolean hasLinkTargetAnnotation(Method method) {
        return (method.isAnnotationPresent(LinkTarget.class));
    }

    public String getLinkTarget(Method method) {
        LinkTarget linkTarget = method.getAnnotation(LinkTarget.class);

        if (linkTarget.value() == null || "".equals(linkTarget.value())) {
            throw new LinkBuilderException("@LinkTarget for " +method+ " must not be null of empty string!");
        }

        return (method.getDeclaringClass().getName() + ":" + linkTarget.value());
    }
}
