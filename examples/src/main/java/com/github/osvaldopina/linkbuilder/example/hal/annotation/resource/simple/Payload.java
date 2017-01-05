package com.github.osvaldopina.linkbuilder.example.hal.annotation.resource.simple;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.osvaldopina.linkbuilder.hal.annotation.HalLink;
import com.github.osvaldopina.linkbuilder.hal.annotation.HalLinks;
import com.github.osvaldopina.linkbuilder.hal.annotation.Param;
import org.springframework.hateoas.ResourceSupport;

@HalLinks({
        @HalLink(controller = RootRestController.class,
                rel = "root",
                overridedRel = "self"),
        @HalLink(controller = RootRestController.class,
                rel = "direct-link",
                hreflang = "href-lang-1" ,
                params = {
                        @Param(name = "query", value = "#payload.queryValue"),
                        @Param(name = "path", value = "#payload.pathValue")
                }),
        @HalLink(controller = RootRestController.class,
                rel = "direct-link",
                hreflang = "href-lang-2",
                overridedRel = "direct-link-overrided",
                params = {
                        @Param(name = "query", value = "#payload.queryValue"),
                        @Param(name = "path", value = "#payload.pathValue")
                }),
        @HalLink(controller = RootRestController.class,
                rel = "direct-link-templated",
                hreflang = "href-lang-3",
                templated = true,
                params = {
                        @Param(name = "templated", value = "'templated-value'")
                })
})
public class Payload extends ResourceSupport {

    private String queryValue;

    private String pathValue;

    private Map<String, Object> embedded = new HashMap<String, Object>();

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

    @JsonProperty("_embedded")
    public Map<String, Object> getEmbedded() {
        return embedded;
    }

    public void addEmbedded(String key, Object value) {
        embedded.put(key, value);
    }
}
