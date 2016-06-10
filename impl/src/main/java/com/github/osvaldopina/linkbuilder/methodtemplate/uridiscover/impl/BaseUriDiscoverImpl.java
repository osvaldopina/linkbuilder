package com.github.osvaldopina.linkbuilder.methodtemplate.uridiscover.impl;

import com.github.osvaldopina.linkbuilder.methodtemplate.uridiscover.BaseUriDiscover;
import com.github.osvaldopina.linkbuilder.methodtemplate.uridiscover.requestparts.RequestPartsFactory;
import com.github.osvaldopina.linkbuilder.methodtemplate.uridiscover.requestparts.RequestPartsFactoryList;
import com.github.osvaldopina.linkbuilder.methodtemplate.uridiscover.requestparts.impl.ChainedRequestParts;
import com.github.osvaldopina.linkbuilder.methodtemplate.uridiscover.requestparts.impl.ServletRequestRequestPartsFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

public class BaseUriDiscoverImpl implements BaseUriDiscover {

    private HttpRequestDiscover httpRequestDiscover = new HttpRequestDiscover();

    @Autowired
    private RequestPartsFactoryList requestPartsFactoryList;


    @Override
    public String getBaseUri() {

        HttpServletRequest request = httpRequestDiscover.getCurrentRequest();

        List<RequestPartsFactory> requestPartsFactories = requestPartsFactoryList.getRequestPartsFactories();

        ChainedRequestParts rootChainedRequestParts = requestPartsFactories.get(0).create(request);
        ChainedRequestParts newChainedRequestParts;
        ChainedRequestParts oldChainedRequestParts = rootChainedRequestParts;

        for(int i=1; i< requestPartsFactories.size(); i++) {
            newChainedRequestParts = requestPartsFactories.get(i).create(request);
            oldChainedRequestParts.setNext(newChainedRequestParts);
            oldChainedRequestParts = newChainedRequestParts;

        }

        return rootChainedRequestParts.getUri();

    }

 }