package com.github.osvaldopina.halexpectation;

import java.util.Map;

public class HalLink {

    private Map<String, Object> link;

    public HalLink(Map<String,Object> link) {
        this.link = link;
    }

    public String getRel() {
        return safeString(link.get("rel"));
    }

    public String getHref() {
        return safeString(link.get("href"));
    }

    public boolean templated() {
        return link.get("templated") != null && "true".equals(link.get("templated"));
    }

    public String getType() {
        return safeString(link.get("type"));
    }

    public String getDeprecation() {
        return safeString(link.get("deprecation"));
    }

    public String getName() {
        return safeString(link.get("name"));
    }

    public String getTitle() {
        return safeString(link.get("title"));
    }

    public String getHreflang() {
        return safeString(link.get("hreflang"));
    }

    private String safeString(Object rel) {
        return rel == null?null:rel.toString();
    }




}
