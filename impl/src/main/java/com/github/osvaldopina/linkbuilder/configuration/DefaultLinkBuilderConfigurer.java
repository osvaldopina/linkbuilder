package com.github.osvaldopina.linkbuilder.configuration;

import com.github.osvaldopina.linkbuilder.urigeneration.base.BaseUriDiscover;
import com.github.osvaldopina.linkbuilder.urigeneration.base.impl.BaseUriDiscoverImpl;
import com.github.osvaldopina.linkbuilder.urigeneration.base.requestparts.RequestPartsFactoryList;
import com.github.osvaldopina.linkbuilder.urigeneration.base.requestparts.impl.RequestPartsFactoryListImpl;
import com.github.osvaldopina.linkbuilder.expression.ExpressionExecutor;
import com.github.osvaldopina.linkbuilder.expression.springhateoas.ExpressionExecutorImpl;

public class DefaultLinkBuilderConfigurer implements LinkBuilderConfigurer {

    @Override
    public RequestPartsFactoryList requestPartsFactoryList() {
        return new RequestPartsFactoryListImpl();
    }

    @Override
    public ExpressionExecutor spelExecutor() {
        return new ExpressionExecutorImpl();
    }

    @Override
    public BaseUriDiscover baseUriDiscover() {
        return new BaseUriDiscoverImpl();
    }

}
