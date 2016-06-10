package com.github.osvaldopina.linkbuilder.direct;

import com.github.osvaldopina.linkbuilder.annotation.Link;
import org.springframework.context.ApplicationContext;
import org.springframework.hateoas.ResourceSupport;

public class DirectLinkContext {

    private Link link;

    private ResourceSupport methodResult;

    private Object[] methodParams;

    private ApplicationContext applicationContext;

    public DirectLinkContext(Link link, ResourceSupport methodResult, Object[] methodParams,
                             ApplicationContext applicationContext) {
        this.link = link;
        this.methodResult = methodResult;
        this.methodParams = methodParams;
        this.applicationContext = applicationContext;
    }

    public boolean linkHasCondition() {
        return link.when() != null;
    }
}
