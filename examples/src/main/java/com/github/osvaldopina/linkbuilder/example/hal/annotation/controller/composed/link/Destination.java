package com.github.osvaldopina.linkbuilder.example.hal.annotation.controller.composed.link;

public enum Destination {

    DIRECT_LINK("direct-link"),

    DIRECT_LINK_TEMPLATED("direct-link-templated");

    private String rel;

    Destination(String rel) {
        this.rel = rel;
    }

    public String getRel() {
        return rel;
    }
}

