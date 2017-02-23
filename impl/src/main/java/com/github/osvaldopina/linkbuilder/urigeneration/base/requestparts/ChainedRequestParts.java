package com.github.osvaldopina.linkbuilder.urigeneration.base.requestparts;

import com.github.osvaldopina.linkbuilder.LinkBuilderException;
import org.springframework.util.Assert;

import java.net.URI;
import java.net.URISyntaxException;

public class ChainedRequestParts  {


    private String scheme;

    private String host;

    private int port;

    private String contextPath;

    private ChainedRequestParts next;

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

    public void setNext(ChainedRequestParts next) {
        this.next = next;
    }


    public String getScheme() {
        if (scheme != null) {
            return scheme;
        }
        else {
            return getNext().getScheme();
        }
    }

    public String getHost() {
        if (host != null) {
            return host;
        }
        return getNext().getHost();
    }

    public int getPort() {
        if (port != -1) {
            return port;
        }
        return getNext().getPort();
    }

    public String getContextPath() {
        if (contextPath != null) {
            return contextPath;
        }
        return getNext().getContextPath();
    }

    public ChainedRequestParts getNext() {
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
