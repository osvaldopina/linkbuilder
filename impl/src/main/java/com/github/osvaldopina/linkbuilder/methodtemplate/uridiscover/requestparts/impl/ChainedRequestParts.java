package com.github.osvaldopina.linkbuilder.methodtemplate.uridiscover.requestparts.impl;

import com.github.osvaldopina.linkbuilder.LinkBuilderException;
import com.github.osvaldopina.linkbuilder.methodtemplate.uridiscover.requestparts.RequestParts;
import org.springframework.util.Assert;


import java.net.URI;
import java.net.URISyntaxException;

public class ChainedRequestParts implements RequestParts {


    private String scheme;

    private String host;

    private int port;

    private String contextPath;

    private RequestParts next;

    public ChainedRequestParts() {
        this.scheme = null;
        this.host = null;
        this.port = -1;
        this.contextPath = null;
    }

    public ChainedRequestParts(String scheme, String host, int port, String contextPath) {
        this.scheme = scheme;
        this.host = host;
        this.port = port;
        this.contextPath = contextPath;
    }

    public void setNext(RequestParts next) {
        this.next = next;
    }


    @Override
    public String getScheme() {
        if (scheme != null) {
            return scheme;
        }
        else {
            return getNext().getScheme();
        }
    }

    @Override
    public String getHost() {
        if (host != null) {
            return host;
        }
        return getNext().getHost();
    }

    @Override
    public int getPort() {
        if (port != -1) {
            return port;
        }
        return getNext().getPort();
    }

    @Override
    public String getContextPath() {
        if (contextPath != null) {
            return contextPath;
        }
        return getNext().getContextPath();
    }

    public RequestParts getNext() {
        Assert.notNull(next, "Next ChainedRequestParts must not be null");
        return next;
    }

    public String getUri() {
        int normalizedPort = getPort();

        if (getScheme().equals("http") && getPort() == 80 || getScheme().equals("https") && getPort() == 443) {
            normalizedPort = -1;
        }

        try {
            return (new URI(getScheme(),null, getHost(), normalizedPort,getContextPath(), null, null)).toString();
        } catch (URISyntaxException e) {
            throw new LinkBuilderException("Could not discover base uri because " +e,e);
        }
    }
}
