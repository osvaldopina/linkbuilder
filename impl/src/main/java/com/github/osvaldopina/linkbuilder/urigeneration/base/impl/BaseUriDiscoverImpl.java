package com.github.osvaldopina.linkbuilder.urigeneration.base.impl;

import com.github.osvaldopina.linkbuilder.urigeneration.base.BaseUriDiscover;
import com.github.osvaldopina.linkbuilder.urigeneration.base.requestparts.RequestPartsFactory;
import com.github.osvaldopina.linkbuilder.urigeneration.base.requestparts.RequestPartsFactoryList;
import com.github.osvaldopina.linkbuilder.urigeneration.base.requestparts.ChainedRequestParts;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

// TODO separate structure from data...
public class BaseUriDiscoverImpl implements BaseUriDiscover {

    private HttpRequestDiscover httpRequestDiscover = HttpRequestDiscover.INSTANCE;

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