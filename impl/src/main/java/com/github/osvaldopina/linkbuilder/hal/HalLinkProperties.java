package com.github.osvaldopina.linkbuilder.hal;

import com.github.osvaldopina.linkbuilder.BaseLinkProperties;
import com.github.osvaldopina.linkbuilder.LinkProperties;
import com.github.osvaldopina.linkbuilder.LinkPropertiesDelegate;

public class HalLinkProperties extends LinkPropertiesDelegate {

    private String hreflang;

    public HalLinkProperties(LinkProperties linkProperties) {
        super(linkProperties);
    }

    public String getHreflang() {
        return hreflang;
    }

    public void setHrefLang(String hreflang) {
        this.hreflang = hreflang;
    }
}
