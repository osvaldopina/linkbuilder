package com.github.osvaldopina.linkbuilder.configuration;

import com.github.osvaldopina.linkbuilder.urigeneration.base.BaseUriDiscover;
import com.github.osvaldopina.linkbuilder.urigeneration.base.requestparts.RequestPartsFactoryList;
import com.github.osvaldopina.linkbuilder.expression.ExpressionExecutor;

public class CustomLinkBuilderConfigurer implements LinkBuilderConfigurer {

    @Override
    public RequestPartsFactoryList requestPartsFactoryList() {
        return null;
    }

    @Override
    public ExpressionExecutor spelExecutor() {
        return null;
    }

    @Override
    public BaseUriDiscover baseUriDiscover() {
        return null;
    }

}
