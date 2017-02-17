package com.github.osvaldopina.linkbuilder.hal;

import com.github.osvaldopina.linkbuilder.BaseLinkBuilder;

public class HalLinkBuilderImpl extends BaseLinkBuilder implements HalLinkBuilder {

    private HalLinkProperties halLinkProperties;

    public HalLinkBuilderImpl(BaseLinkBuilder baseLinkBuilder) {
        super(baseLinkBuilder);
        halLinkProperties = new HalLinkProperties(baseLinkBuilder.getLinkProperties());
        baseLinkBuilder.setLinkProperties(halLinkProperties);
    }

    @Override
    public HalLinkBuilder hreflang(String hreflang) {
        halLinkProperties.setHrefLang(hreflang);
        return this;
    }
}
