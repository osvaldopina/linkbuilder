package com.github.osvaldopina.linkbuilder.configuration;

import com.github.osvaldopina.linkbuilder.methodtemplate.LinkGenerator;
import com.github.osvaldopina.linkbuilder.methodtemplate.TemplateGenerator;
import com.github.osvaldopina.linkbuilder.methodtemplate.uridiscover.BaseUriDiscover;
import com.github.osvaldopina.linkbuilder.methodtemplate.uridiscover.requestparts.RequestPartsFactoryList;
import com.github.osvaldopina.linkbuilder.spel.SpelExecutor;

public interface LinkBuilderConfigurer {

    TemplateGenerator templateGenerator();

    RequestPartsFactoryList requestPartsFactoryList();

    SpelExecutor spelExecutor();

    BaseUriDiscover baseUriDiscover();

    LinkGenerator linkGenerator();
}
