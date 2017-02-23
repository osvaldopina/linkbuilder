package com.github.osvaldopina.linkbuilder.urigeneration.base.requestparts.impl;

import com.github.osvaldopina.linkbuilder.urigeneration.base.requestparts.ChainedRequestParts;
import com.github.osvaldopina.linkbuilder.urigeneration.base.requestparts.RequestPartsFactory;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ForwardedHostHeaderPartsFactory implements RequestPartsFactory {


    private static final Pattern FORWARDED_HOST_PATTERN = Pattern.compile("host=\"?([^;,\"]+)\"?");

    private static final Pattern FORWARDED_PROTO_PATTERN = Pattern.compile("proto=\"?([^;,\"]+)\"?");


    @Override
    public ChainedRequestParts create(HttpServletRequest request) {

        String host = null;
        String scheme = null;

        String forwardedHeader = request.getHeader("Forwarded")==null?
                "":
                request.getHeaders("Forwarded").nextElement().toString();

        if (StringUtils.hasText(forwardedHeader)) {
            String forwardedToUse = StringUtils.commaDelimitedListToStringArray(forwardedHeader)[0];
            Matcher m = FORWARDED_HOST_PATTERN.matcher(forwardedToUse);
            if (m.find()) {
                host = m.group(1).trim();
            }
            m = FORWARDED_PROTO_PATTERN.matcher(forwardedToUse);
            if (m.find()) {
                scheme = m.group(1).trim();
            }
        }


        return new ChainedRequestParts(scheme, host, -1, null);
    }
}
