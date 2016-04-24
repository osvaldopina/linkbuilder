package com.github.osvaldopina.halexpectation;

public class HalLink {

    private String rel;

    private String href;

    public HalLink(String rel, String href) {
        this.rel = rel;
        this.href = href;
    }

    public String getRel() {
        return rel;
    }

    public String getHref() {
        return href;
    }
}
