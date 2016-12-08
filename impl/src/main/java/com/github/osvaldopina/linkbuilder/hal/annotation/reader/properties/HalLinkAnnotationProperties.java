package com.github.osvaldopina.linkbuilder.hal.annotation.reader.properties;

import com.github.osvaldopina.linkbuilder.annotation.reader.properties.LinkAnnotationParameter;
import com.github.osvaldopina.linkbuilder.annotation.reader.properties.LinkAnnotationProperties;

import java.util.List;

public class HalLinkAnnotationProperties extends LinkAnnotationProperties {

    private String hreflang;

    public HalLinkAnnotationProperties(
            String destination,
            String rel,
            boolean templated,
            String hreflang,
            List<LinkAnnotationParameter> parameters) {

        super(destination, rel, templated, parameters);
        this.hreflang = hreflang;
    }

    public String getHreflang() {
        return hreflang;
    }
}
