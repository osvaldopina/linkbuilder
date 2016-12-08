package com.github.osvaldopina.linkbuilder.hal.springhateoas;

import org.springframework.hateoas.Link;

public class HalLink extends Link {

    private String hreflang;

    public HalLink(String uri, String rel, String hreflang) {
        super(uri, rel);
        this.hreflang = hreflang;
    }

    public String getHreflang() {
        return hreflang;
    }

}
