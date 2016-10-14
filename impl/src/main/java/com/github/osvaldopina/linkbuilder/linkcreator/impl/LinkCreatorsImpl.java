package com.github.osvaldopina.linkbuilder.linkcreator.impl;

import com.github.osvaldopina.linkbuilder.LinkBuilder;
import com.github.osvaldopina.linkbuilder.LinkBuilderException;
import com.github.osvaldopina.linkbuilder.linkcreator.LinkCreator;
import com.github.osvaldopina.linkbuilder.linkcreator.LinkCreators;

import java.util.ArrayList;
import java.util.List;

public class LinkCreatorsImpl implements LinkCreators{


    private List<LinkCreator> linkCreators = new ArrayList<LinkCreator>();

    public LinkCreatorsImpl(List<LinkCreator> linkCreators) {
        this.linkCreators.addAll(linkCreators);
    }


    @Override
    public  LinkCreator get(Class<?> linkBuilderType, Class<?> linkType) {

        for(LinkCreator linkCreator: linkCreators) {
            if (linkCreator.canCreate(linkBuilderType, linkType)) {
                return linkCreator;
            }
        }
        throw new LinkBuilderException(
                "Could not find LinkCreator for linkbuiler type " + linkBuilderType + " and link type " + linkType
        );
    }
}
