package com.github.osvaldopina.linkbuilder.configuration;

import com.github.osvaldopina.linkbuilder.urigeneration.base.BaseUriDiscover;
import com.github.osvaldopina.linkbuilder.urigeneration.base.requestparts.RequestPartsFactoryList;
import com.github.osvaldopina.linkbuilder.expression.ExpressionExecutor;

public interface LinkBuilderConfigurer {

    RequestPartsFactoryList requestPartsFactoryList();

    ExpressionExecutor spelExecutor();

    BaseUriDiscover baseUriDiscover();

}
