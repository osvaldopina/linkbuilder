package com.github.osvaldopina.linkbuilder.example.extensions.requestpartsfactorylist;

import com.github.osvaldopina.linkbuilder.urigeneration.base.requestparts.RequestPartsFactory;
import com.github.osvaldopina.linkbuilder.urigeneration.base.requestparts.ChainedRequestParts;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class CustomHeaderPartsFactory implements RequestPartsFactory {


    @Override
    public ChainedRequestParts create(HttpServletRequest request) {

        String protocolHeader = request.getHeader("my-custom-header")==null?
                "":
                request.getHeaders("my-custom-header").nextElement().toString();

        if (StringUtils.hasText(protocolHeader)) {
            Map<String, String> headers = fromStringToMap(protocolHeader);

            return new ChainedRequestParts(
                    headers.get("scheme"),
                    headers.get("host"),
                    headers.get("port") == null?-1:Integer.valueOf(headers.get("port")),
                    headers.get("contextPath")
            );

        }
        else {
            return new ChainedRequestParts();
        }

    }

    private Map<String,String> fromStringToMap(String value) {
        Map<String, String> tmp = new HashMap<String, String>();
        String[] pairs = value.split(",");
        for (int i = 0; i < pairs.length; i++) {
            String pair = pairs[i];
            String[] keyValue = pair.split(":");
            tmp.put(keyValue[0], keyValue[1]);
        }
        return tmp;
    }
}
