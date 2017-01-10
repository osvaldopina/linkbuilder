package com.github.osvaldopina.linkbuilder.urigeneration.base.requestparts.impl;

import com.github.osvaldopina.linkbuilder.urigeneration.base.requestparts.RequestPartsFactory;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

public class XForwardedProtoHeaderPartsFactory implements RequestPartsFactory {


    @Override
    public ChainedRequestParts create(HttpServletRequest request) {

        String scheme = null;

        String protocolHeader = request.getHeader("X-Forwarded-Proto")==null?
                "":request.getHeaders("X-Forwarded-Proto").nextElement().toString();

        if (StringUtils.hasText(protocolHeader)) {
            String[] protocols = StringUtils.commaDelimitedListToStringArray(protocolHeader);
            scheme = protocols[0];
        }

        return new ChainedRequestParts(scheme, null, -1, null);
    }
}
