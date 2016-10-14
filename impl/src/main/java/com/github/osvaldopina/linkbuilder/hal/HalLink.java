package com.github.osvaldopina.linkbuilder.hal;

import org.springframework.hateoas.Link;

public class HalLink extends Link {

    private String type;

    private String profile;

    private String title;

    private String hrefLang;

    public HalLink(String href, String rel, String type, String profile, String title, String hrefLang) {
        super(href, rel);
        this.type = type;
        this.profile = profile;
        this.title = title;
        this.hrefLang = hrefLang;
    }


    public String getType() {
        return type;
    }

    public String getProfile() {
        return profile;
    }

    public String getTitle() {
        return title;
    }

    public String getHrefLang() {
        return hrefLang;
    }
}
