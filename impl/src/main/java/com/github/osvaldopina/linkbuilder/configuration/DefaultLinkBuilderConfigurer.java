package com.github.osvaldopina.linkbuilder.configuration;

import com.github.osvaldopina.linkbuilder.expression.ExpressionExecutor;
import com.github.osvaldopina.linkbuilder.expression.impl.ExpressionExecutorImpl;
import com.github.osvaldopina.linkbuilder.methodtemplate.templategenerator.MethodTemplateGenerator;
import com.github.osvaldopina.linkbuilder.methodtemplate.templategenerator.impl.MethodTemplateGeneratorImpl;
import com.github.osvaldopina.linkbuilder.methodtemplate.uridiscover.BaseUriDiscover;
import com.github.osvaldopina.linkbuilder.methodtemplate.uridiscover.impl.BaseUriDiscoverImpl;
import com.github.osvaldopina.linkbuilder.methodtemplate.uridiscover.impl.MethodCallUriGeneratorImpl;
import com.github.osvaldopina.linkbuilder.methodtemplate.uridiscover.requestparts.RequestPartsFactoryList;
import com.github.osvaldopina.linkbuilder.methodtemplate.uridiscover.requestparts.impl.RequestPartsFactoryListImpl;
import com.github.osvaldopina.linkbuilder.methodtemplate.urigenerator.MethodCallUriGenerator;

public class DefaultLinkBuilderConfigurer implements LinkBuilderConfigurer {

    @Override
    public MethodTemplateGenerator templateGenerator() {
        return new MethodTemplateGeneratorImpl();
    }

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

    @Override
    public MethodCallUriGenerator linkGenerator() {
        return new MethodCallUriGeneratorImpl();
    }
}
