package com.github.osvaldopina.linkbuilder.linkcreator.impl;

import com.github.osvaldopina.linkbuilder.linkcreator.LinkCreator;
import com.github.osvaldopina.linkbuilder.impl.springhateoas.SpringHateoasLinkBuilderImpl;
import org.springframework.hateoas.Link;

public class SpringHateoasLinkCreator implements LinkCreator {



    @Override
    public boolean canCreate(Class<?> linkBuilderType, Class<?> linkType) {
        return SpringHateoasLinkBuilderImpl.class.isAssignableFrom(linkBuilderType) &&
                Link.class.isAssignableFrom(linkType);
    }

    @Override
    public Object create(String uri, Object linkBuilder) {
        return new Link(uri, ((SpringHateoasLinkBuilderImpl) linkBuilder).getRel());
    }
}
