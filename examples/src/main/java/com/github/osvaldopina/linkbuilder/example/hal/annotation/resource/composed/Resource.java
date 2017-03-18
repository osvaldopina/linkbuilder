package com.github.osvaldopina.linkbuilder.example.hal.annotation.resource.composed;

import com.github.osvaldopina.linkbuilder.annotation.SelfFromCurrentCall;
import com.github.osvaldopina.linkbuilder.example.hal.annotation.resource.composed.link.Destination;
import com.github.osvaldopina.linkbuilder.example.hal.annotation.resource.composed.link.MyHalLink;
import com.github.osvaldopina.linkbuilder.example.hal.annotation.resource.composed.link.MyHalLinks;
import com.github.osvaldopina.linkbuilder.hal.annotation.Variable;
import org.springframework.hateoas.ResourceSupport;

@SelfFromCurrentCall
@MyHalLinks({
        @MyHalLink(destination = Destination.DIRECT_LINK,
                hreflang = "href-lang-1" ,
                variables = {
                        @Variable(name = "query", value = "#resource.queryValue"),
                        @Variable(name = "path", value = "#resource.pathValue")
                }),
        @MyHalLink(destination = Destination.DIRECT_LINK,
                hreflang = "href-lang-2",
                overridedRel = "direct-link-overrided",
                variables = {
                        @Variable(name = "query", value = "#resource.queryValue"),
                        @Variable(name = "path", value = "#resource.pathValue")
                }),
        @MyHalLink(destination = Destination.DIRECT_LINK_TEMPLATED,
                hreflang = "href-lang-3",
                templated = true,
                variables = {
                        @Variable(name = "templated", value = "'templated-value'")
                })
})
public class Resource extends ResourceSupport {

    private String queryValue;

    private String pathValue;

    public String getQueryValue() {
        return queryValue;
    }

    public void setQueryValue(String queryValue) {
        this.queryValue = queryValue;
    }

    public String getPathValue() {
        return pathValue;
    }

    public void setPathValue(String pathValue) {
        this.pathValue = pathValue;
    }
}
