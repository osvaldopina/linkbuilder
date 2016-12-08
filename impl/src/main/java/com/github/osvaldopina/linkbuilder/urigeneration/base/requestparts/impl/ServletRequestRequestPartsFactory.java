package com.github.osvaldopina.linkbuilder.urigeneration.base.requestparts.impl;

import com.github.osvaldopina.linkbuilder.LinkBuilderException;
import com.github.osvaldopina.linkbuilder.urigeneration.base.requestparts.RequestPartsFactory;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.net.URISyntaxException;

public class ServletRequestRequestPartsFactory implements RequestPartsFactory {


    @Override
    public ChainedRequestParts create(HttpServletRequest request) {

        URI uri;
        try {
            uri = new URI(request.getRequestURL().toString());
        } catch (URISyntaxException e) {
            throw new LinkBuilderException("Could not get current request url because " +e,e);
        }
        String scheme = uri.getScheme();
        String host = uri.getHost();
        int port = uri.getPort();
        if (port ==-1) {
            port = scheme.equalsIgnoreCase("http") ? 80 : 443;
        }
        return new ChainedRequestParts(scheme, host, port, request.getContextPath());
    }
}
