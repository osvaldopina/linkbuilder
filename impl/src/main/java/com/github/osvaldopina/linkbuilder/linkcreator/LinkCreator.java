package com.github.osvaldopina.linkbuilder.linkcreator;

import com.github.osvaldopina.linkbuilder.LinkBuilder;

public interface LinkCreator {


    boolean canCreate(LinkBuilder linkBuilder);

    Object create(String uri, LinkBuilder linkBuilder);

    void createAndSet(String uri, LinkBuilder linkBuilder, Object payLoad);


}
