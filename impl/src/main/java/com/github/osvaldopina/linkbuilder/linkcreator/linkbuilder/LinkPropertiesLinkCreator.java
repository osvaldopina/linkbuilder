package com.github.osvaldopina.linkbuilder.linkcreator.linkbuilder;

import com.github.osvaldopina.linkbuilder.BaseLinkProperties;
import com.github.osvaldopina.linkbuilder.LinkProperties;

public interface LinkPropertiesLinkCreator {


    boolean canCreate(LinkProperties linkProperties);

    Object create(LinkProperties linkProperties);

    void createAndSet(LinkProperties linkProperties);


}
