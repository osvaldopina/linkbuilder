package com.github.osvaldopina.linkbuilder.example.annotation.resource.composed;

import com.github.osvaldopina.linkbuilder.annotation.Param;
import com.github.osvaldopina.linkbuilder.example.annotation.resource.composed.link.LINK_DESTINATION;
import com.github.osvaldopina.linkbuilder.example.annotation.resource.composed.link.MyLink;
import com.github.osvaldopina.linkbuilder.example.annotation.resource.composed.link.MyLinks;
import org.springframework.hateoas.ResourceSupport;

@MyLinks(value = {
        @MyLink(destination = LINK_DESTINATION.ROOT, overridedRel = "self"),
        @MyLink(destination = LINK_DESTINATION.DIRECT_LINK, params = {
                @Param(name = "query", value = "#payload.queryValue"),
                @Param(name = "path", value = "#payload.pathValue")
        }),
        @MyLink(destination = LINK_DESTINATION.DIRECT_LINK, overridedRel = "direct-link-overrided",
                params = {
                        @Param(name = "query", value = "#payload.queryValue"),
                        @Param(name = "path", value = "#payload.pathValue")
                }),
        @MyLink(destination = LINK_DESTINATION.DIRECT_LINK_TEMPLATED,
                templated = true,
                params = {
                        @Param(name = "templated", value = "'templated-value'")
                })
})
public class Payload extends ResourceSupport {

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
