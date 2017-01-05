package com.github.osvaldopina.linkbuilder.hal.springhateoas;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.hateoas.Link;

public class HalLink extends Link {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String hreflang;

    public HalLink(String uri, String rel, String hreflang) {
        super(uri, rel);
        this.hreflang = hreflang;
    }

    public String getHreflang() {
        return hreflang;
    }

}
