package com.github.osvaldopina.linkbuilder.linkcreator;

import com.github.osvaldopina.linkbuilder.LinkBuilder;

public interface LinkCreator {


    boolean canCreate(Class<?> linkBuilderType, Class<?> linkType);

    Object create(String uri, Object linkBuilder);


}
