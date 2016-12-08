package com.github.osvaldopina.linkbuilder.configuration;

import com.github.osvaldopina.linkbuilder.LinkBuilderException;
import com.github.osvaldopina.linkbuilder.urigeneration.base.BaseUriDiscover;
import com.github.osvaldopina.linkbuilder.urigeneration.base.requestparts.RequestPartsFactoryList;
import com.github.osvaldopina.linkbuilder.expression.ExpressionExecutor;

public class ChainedLinkBuilderConfigurer implements LinkBuilderConfigurer {

    private LinkBuilderConfigurer[] configurers = new LinkBuilderConfigurer[2];


    public ChainedLinkBuilderConfigurer(LinkBuilderConfigurer defaultConfigurer,
                                        LinkBuilderConfigurer customLinkBuilderConfigurer) {
        configurers[0] = customLinkBuilderConfigurer;
        configurers[1] = defaultConfigurer;
    }


    @Override
    public RequestPartsFactoryList requestPartsFactoryList() {
        return getConfigurer().requestPartsFactoryList();
    }

    @Override
    public ExpressionExecutor spelExecutor() {
        return getConfigurer().spelExecutor();
    }

    @Override
    public BaseUriDiscover baseUriDiscover() {
        return getConfigurer().baseUriDiscover();
    }

    private LinkBuilderConfigurer getConfigurer() {
        for(LinkBuilderConfigurer configurer:configurers) {
            if (configurer != null) {
                return configurer;
            }
        }
        throw new LinkBuilderException("Could not find a configurer (" + LinkBuilderConfigurer.class.getName() +")");
    }
}
