package com.github.osvaldopina.linkbuilder.configuration;

import com.github.osvaldopina.linkbuilder.LinkBuilderException;
import com.github.osvaldopina.linkbuilder.expression.ExpressionExecutor;
import com.github.osvaldopina.linkbuilder.methodtemplate.templategenerator.MethodTemplateGenerator;
import com.github.osvaldopina.linkbuilder.methodtemplate.uridiscover.BaseUriDiscover;
import com.github.osvaldopina.linkbuilder.methodtemplate.uridiscover.requestparts.RequestPartsFactoryList;
import com.github.osvaldopina.linkbuilder.methodtemplate.urigenerator.MethodCallUriGenerator;

public class ChainedLinkBuilderConfigurer implements LinkBuilderConfigurer {

    private LinkBuilderConfigurer[] configurers = new LinkBuilderConfigurer[2];


    public ChainedLinkBuilderConfigurer(LinkBuilderConfigurer defaultConfigurer,
                                        LinkBuilderConfigurer customLinkBuilderConfigurer) {
        configurers[0] = customLinkBuilderConfigurer;
        configurers[1] = defaultConfigurer;
    }

    @Override
    public MethodTemplateGenerator templateGenerator() {
        return getConfigurer().templateGenerator();
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

    @Override
    public MethodCallUriGenerator linkGenerator() {
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
