package com.github.osvaldopina.linkbuilder.hal.annotation.reader.properties;

import com.github.osvaldopina.linkbuilder.annotation.reader.properties.LinkAnnotationVariable;
import com.github.osvaldopina.linkbuilder.annotation.reader.properties.LinkAnnotationProperties;

import java.util.List;

public class HalLinkAnnotationProperties extends LinkAnnotationProperties {

    private String hreflang;

    public HalLinkAnnotationProperties(
            String destination,
            String rel,
            boolean templated,
            String hreflang,
            List<LinkAnnotationVariable> parameters) {

        super(destination, rel, templated, parameters);
        if (!"".equals(hreflang.trim())) {
            this.hreflang = hreflang;
        }
    }

    public String getHreflang() {
        return hreflang;
    }
}
