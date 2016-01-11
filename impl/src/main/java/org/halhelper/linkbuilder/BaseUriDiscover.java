package org.halhelper.linkbuilder;

import org.springframework.context.ApplicationContext;
import org.springframework.util.StringUtils;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by osvaldopina on 1/8/16.
 */
public class BaseUriDiscover {

    private static final Pattern FORWARDED_HOST_PATTERN = Pattern.compile("host=\"?([^;,\"]+)\"?");

    private static final Pattern FORWARDED_PROTO_PATTERN = Pattern.compile("proto=\"?([^;,\"]+)\"?");


    public String getBaseUri(HttpServletRequest request, ApplicationContext applicationContext) {
        URI uri = null;
        try {
            uri = new URI(request.getRequestURL().toString());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        String scheme = uri.getScheme();
        String host = uri.getHost();
        int port = uri.getPort();

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
        else {
            String hostHeader = request.getHeader("X-Forwarded-Host")==null?
                    "":
                    request.getHeaders("X-Forwarded-Host").nextElement().toString();

            if (StringUtils.hasText(hostHeader)) {
                String[] hosts = StringUtils.commaDelimitedListToStringArray(hostHeader);
                String hostToUse = hosts[0];
                if (hostToUse.contains(":")) {
                    String[] hostAndPort = StringUtils.split(hostToUse, ":");
                    host = hostAndPort[0];
                    port = Integer.parseInt(hostAndPort[1]);
                }
                else {
                    host = hostToUse;
                    port = -1;
                }
            }

            String portHeader = request.getHeader("X-Forwarded-Port")==null?
                    "":
                    request.getHeaders("X-Forwarded-Port").nextElement().toString();

            if (StringUtils.hasText(portHeader)) {
                String[] ports = StringUtils.commaDelimitedListToStringArray(portHeader);
                port = Integer.parseInt(ports[0]);
            }

            String protocolHeader = request.getHeader("X-Forwarded-Proto")==null?
                    "":
                    request.getHeaders("X-Forwarded-Proto").nextElement().toString();

            if (StringUtils.hasText(protocolHeader)) {
                String[] protocols = StringUtils.commaDelimitedListToStringArray(protocolHeader);
                scheme = protocols[0];
            }
        }


        if (scheme.equals("http") && port == 80 || scheme.equals("https") && port == 443) {
            port = -1;
        }

        URI baseUri = null;
        try {
            baseUri = new URI(scheme,null, host, port, null, null, null);
        } catch (URISyntaxException e) {
            throw new LinkBuilderException("Could not discover base uri because " +e,e);
        }

        return baseUri.toString() + getContextPath(applicationContext);

    }

    public String getContextPath(ApplicationContext applicationContext) {
        String contextPath = applicationContext.getEnvironment().getProperty("server.contextPath");

        if (contextPath != null && (!contextPath.trim().equals(""))) {
            return contextPath.startsWith("/")?contextPath:"/" + contextPath;
        }
        else {
            return "";
        }




    }

}
