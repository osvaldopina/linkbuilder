package com.github.osvaldopina.linkbuilder.configuration;

import com.github.osvaldopina.linkbuilder.methodtemplate.LinkGenerator;
import com.github.osvaldopina.linkbuilder.methodtemplate.TemplateGenerator;
import com.github.osvaldopina.linkbuilder.methodtemplate.uridiscover.BaseUriDiscover;
import com.github.osvaldopina.linkbuilder.methodtemplate.uridiscover.requestparts.RequestPartsFactoryList;
import com.github.osvaldopina.linkbuilder.expression.ExpressionExecutor;

public class CustomLinkBuilderConfigurer implements LinkBuilderConfigurer {

    @Override
    public TemplateGenerator templateGenerator() {
        return null;
    }

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

    @Override
    public LinkGenerator linkGenerator() {
        return null;
    }
}
