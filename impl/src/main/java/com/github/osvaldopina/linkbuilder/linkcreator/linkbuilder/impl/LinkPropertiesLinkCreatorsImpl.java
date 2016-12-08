package com.github.osvaldopina.linkbuilder.linkcreator.linkbuilder.impl;

import com.github.osvaldopina.linkbuilder.BaseLinkProperties;
import com.github.osvaldopina.linkbuilder.LinkBuilderException;
import com.github.osvaldopina.linkbuilder.LinkProperties;
import com.github.osvaldopina.linkbuilder.linkcreator.linkbuilder.LinkPropertiesLinkCreator;
import com.github.osvaldopina.linkbuilder.linkcreator.linkbuilder.LinkPropertiesLinkCreators;

import java.util.ArrayList;
import java.util.List;

public class LinkPropertiesLinkCreatorsImpl implements LinkPropertiesLinkCreators {


    private List<LinkPropertiesLinkCreator> linkPropertiesLinkCreators = new ArrayList<LinkPropertiesLinkCreator>();

    public LinkPropertiesLinkCreatorsImpl(List<LinkPropertiesLinkCreator> linkPropertiesLinkCreators) {
        this.linkPropertiesLinkCreators.addAll(linkPropertiesLinkCreators);
    }


    @Override
    public LinkPropertiesLinkCreator get(LinkProperties linkProperties) {

        for(LinkPropertiesLinkCreator linkPropertiesLinkCreator : linkPropertiesLinkCreators) {
            if (linkPropertiesLinkCreator.canCreate(linkProperties)) {
                return linkPropertiesLinkCreator;
            }
        }
        throw new LinkBuilderException(
                "Could not find LinkCreator for type " + linkProperties.getClass());
    }
}
