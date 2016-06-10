package com.github.osvaldopina.linkbuilder.methodtemplate.uridiscover.requestparts.impl;

import com.github.osvaldopina.linkbuilder.methodtemplate.uridiscover.requestparts.RequestPartsFactory;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class XForwardedHostHeaderPartsFactory implements RequestPartsFactory {


    @Override
    public ChainedRequestParts create(HttpServletRequest request) {

        String host = null;
        int port = -1;

        String hostHeader = request.getHeader("X-Forwarded-Host") == null ?
                "" :
                request.getHeaders("X-Forwarded-Host").nextElement().toString();

        if (StringUtils.hasText(hostHeader)) {
            String[] hosts = StringUtils.commaDelimitedListToStringArray(hostHeader);
            String hostToUse = hosts[0];
            if (hostToUse.contains(":")) {
                String[] hostAndPort = StringUtils.split(hostToUse, ":");
                host = hostAndPort[0];
                port = Integer.parseInt(hostAndPort[1]);
            } else {
                host = hostToUse;
                port = -1;
            }


        }
        return new ChainedRequestParts(null, host, port, null);
    }
}
