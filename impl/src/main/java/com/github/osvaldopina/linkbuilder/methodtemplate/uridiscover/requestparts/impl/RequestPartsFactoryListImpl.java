package com.github.osvaldopina.linkbuilder.methodtemplate.uridiscover.requestparts.impl;

import com.github.osvaldopina.linkbuilder.methodtemplate.uridiscover.requestparts.RequestPartsFactory;
import com.github.osvaldopina.linkbuilder.methodtemplate.uridiscover.requestparts.RequestPartsFactoryList;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class RequestPartsFactoryListImpl implements RequestPartsFactoryList {


    List<RequestPartsFactory> requestPartsFactories = Collections.unmodifiableList(Arrays.asList(
            new XForwardedProtoHeaderPartsFactory(),
            new XForwardedPortHeaderPartsFactory(),
            new XForwardedHostHeaderPartsFactory(),
            new ForwardedHostHeaderPartsFactory(),
            new ServletRequestRequestPartsFactory()
    ));


    @Override
    public List<RequestPartsFactory> getRequestPartsFactories() {
        return requestPartsFactories;
    }
}
