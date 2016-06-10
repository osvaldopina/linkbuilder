package com.github.osvaldopina.linkbuilder.methodtemplate.uridiscover.requestparts.impl;

import com.github.osvaldopina.linkbuilder.methodtemplate.uridiscover.requestparts.RequestPartsFactory;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

public class XForwardedPortHeaderPartsFactory implements RequestPartsFactory {


    @Override
    public ChainedRequestParts create(HttpServletRequest request) {

        int port = -1;

        String portHeader = request.getHeader("X-Forwarded-Port")==null?
                "":
                request.getHeaders("X-Forwarded-Port").nextElement().toString();

        if (StringUtils.hasText(portHeader)) {
            String[] ports = StringUtils.commaDelimitedListToStringArray(portHeader);
            port = Integer.parseInt(ports[0]);
        }

        return new ChainedRequestParts(null, null, port, null);
    }
}
