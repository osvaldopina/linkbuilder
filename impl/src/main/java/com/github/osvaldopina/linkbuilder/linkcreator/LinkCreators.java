package com.github.osvaldopina.linkbuilder.linkcreator;

import com.github.osvaldopina.linkbuilder.LinkBuilder;

public interface LinkCreators {

     LinkCreator get(Class<?> linkBuilderType, Class<?> linkType);

}
