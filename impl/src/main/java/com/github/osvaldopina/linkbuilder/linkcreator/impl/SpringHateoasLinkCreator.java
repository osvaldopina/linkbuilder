package com.github.osvaldopina.linkbuilder.linkcreator.impl;

import com.github.osvaldopina.linkbuilder.LinkBuilder;
import com.github.osvaldopina.linkbuilder.linkcreator.LinkCreator;
import com.github.osvaldopina.linkbuilder.impl.springhateoas.SpringHateoasLinkBuilderImpl;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.Assert;

public class SpringHateoasLinkCreator implements LinkCreator {



    @Override
    public boolean canCreate(LinkBuilder linkBuilder) {
        return SpringHateoasLinkBuilderImpl.class.isAssignableFrom(linkBuilder.getClass());
    }

    @Override
    public Object create(String uri, LinkBuilder linkBuilder) {
        return new Link(uri, ((SpringHateoasLinkBuilderImpl) linkBuilder).getRel());
    }


    @Override
    public void createAndSet(String uri, LinkBuilder linkBuilder, Object payload) {
        Assert.notNull(payload);
        Assert.isAssignable(ResourceSupport.class, payload.getClass(), "payload must be a subclass of "
                + ResourceSupport.class);

        Link link = new Link(uri, ((SpringHateoasLinkBuilderImpl) linkBuilder).getRel());
        ResourceSupport resourceSupport = (ResourceSupport) payload;
        resourceSupport.add(link);

    }
}
