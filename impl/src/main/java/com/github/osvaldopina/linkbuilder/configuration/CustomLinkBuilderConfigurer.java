package com.github.osvaldopina.linkbuilder.configuration;

import com.github.osvaldopina.linkbuilder.expression.ExpressionExecutor;
import com.github.osvaldopina.linkbuilder.methodtemplate.templategenerator.MethodTemplateGenerator;
import com.github.osvaldopina.linkbuilder.methodtemplate.uridiscover.BaseUriDiscover;
import com.github.osvaldopina.linkbuilder.methodtemplate.uridiscover.requestparts.RequestPartsFactoryList;
import com.github.osvaldopina.linkbuilder.methodtemplate.urigenerator.MethodCallUriGenerator;

public class CustomLinkBuilderConfigurer implements LinkBuilderConfigurer {

    @Override
    public MethodTemplateGenerator templateGenerator() {
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
    public MethodCallUriGenerator linkGenerator() {
        return null;
    }
}
