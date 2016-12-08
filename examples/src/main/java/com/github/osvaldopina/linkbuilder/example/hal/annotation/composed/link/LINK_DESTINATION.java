package com.github.osvaldopina.linkbuilder.example.hal.annotation.composed.link;

import com.github.osvaldopina.linkbuilder.example.annotation.composed.RootRestController;
import com.github.osvaldopina.linkbuilder.linkdestination.springhateoas.DestinationIdentityFactorty;

public enum LINK_DESTINATION {

    DIRECT_LINK("direct-link"),

    DIRECT_LINK_TEMPLATED("direct-link-templated");

    private String rel;

    LINK_DESTINATION(String rel) {
        this.rel = rel;
    }

    public String getRel() {
        return rel;
    }
}

