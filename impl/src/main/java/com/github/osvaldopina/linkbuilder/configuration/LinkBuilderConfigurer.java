package com.github.osvaldopina.linkbuilder.configuration;

import com.github.osvaldopina.linkbuilder.methodtemplate.urigenerator.MethodCallUriGenerator;
import com.github.osvaldopina.linkbuilder.methodtemplate.templategenerator.MethodTemplateGenerator;
import com.github.osvaldopina.linkbuilder.methodtemplate.uridiscover.BaseUriDiscover;
import com.github.osvaldopina.linkbuilder.methodtemplate.uridiscover.requestparts.RequestPartsFactoryList;
import com.github.osvaldopina.linkbuilder.expression.ExpressionExecutor;

public interface LinkBuilderConfigurer {

    MethodTemplateGenerator templateGenerator();

    RequestPartsFactoryList requestPartsFactoryList();

    ExpressionExecutor spelExecutor();

    BaseUriDiscover baseUriDiscover();

    MethodCallUriGenerator linkGenerator();
}
