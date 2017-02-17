package com.github.osvaldopina.linkbuilder.example.annotation.resource.composed.link;

public enum Destionation {

    ROOT("root"),

    DIRECT_LINK("direct-link"),

    DIRECT_LINK_TEMPLATED("direct-link-templated");

    private String rel;

    Destionation(String rel) {
        this.rel = rel;
    }

    public String getRel() {
        return rel;
    }
}
