package com.github.osvaldopina.linkbuilder.configuration;

import com.github.osvaldopina.linkbuilder.LinkBuilderException;
import com.github.osvaldopina.linkbuilder.methodtemplate.LinkGenerator;
import com.github.osvaldopina.linkbuilder.methodtemplate.TemplateGenerator;
import com.github.osvaldopina.linkbuilder.methodtemplate.uridiscover.BaseUriDiscover;
import com.github.osvaldopina.linkbuilder.methodtemplate.uridiscover.requestparts.RequestPartsFactoryList;
import com.github.osvaldopina.linkbuilder.spel.SpelExecutor;

import java.util.ArrayList;
import java.util.List;

public class ChainedLinkBuilderConfigurer implements LinkBuilderConfigurer {

    private LinkBuilderConfigurer[] configurers = new LinkBuilderConfigurer[2];


    public ChainedLinkBuilderConfigurer(LinkBuilderConfigurer defaultConfigurer,
                                        LinkBuilderConfigurer customLinkBuilderConfigurer) {
        configurers[0] = customLinkBuilderConfigurer;
        configurers[1] = defaultConfigurer;
    }

    @Override
    public TemplateGenerator templateGenerator() {
        return getConfigurer().templateGenerator();
    }

    @Override
    public RequestPartsFactoryList requestPartsFactoryList() {
        return getConfigurer().requestPartsFactoryList();
    }

    @Override
    public SpelExecutor spelExecutor() {
        return getConfigurer().spelExecutor();
    }

    @Override
    public BaseUriDiscover baseUriDiscover() {
        return getConfigurer().baseUriDiscover();
    }

    @Override
    public LinkGenerator linkGenerator() {
        return getConfigurer().linkGenerator();
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
