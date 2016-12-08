package com.github.osvaldopina.linkbuilder.example.annotation.simple;

import org.springframework.hateoas.ResourceSupport;

public class Payload extends ResourceSupport {

    private String queryValue;

    private String pathValue;

    public String getQueryValue() {
        return queryValue;
    }

    public void setQueryValue(String queryValue) {
        this.queryValue = queryValue;
    }

    public String getPathValue() {
        return pathValue;
    }

    public void setPathValue(String pathValue) {
        this.pathValue = pathValue;
    }
}
