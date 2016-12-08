package com.github.osvaldopina.linkbuilder.annotation.reader.properties;

import java.util.ArrayList;
import java.util.List;

public class LinkAnnotationProperties {

    private String destination;

    private String rel;

    private boolean templated;

    private List<LinkAnnotationParameter> parameters = new ArrayList<LinkAnnotationParameter>();

    public LinkAnnotationProperties(String destination, String rel, boolean templated, List<LinkAnnotationParameter> parameters) {
        this.destination = destination;
        this.rel = rel;
        this.templated = templated;
        this.parameters.addAll(parameters);
    }


    public String getDestination() {
        return destination;
    }

    public String getRel() {
        return rel;
    }

    public boolean isTemplated() {
        return templated;
    }

    public List<LinkAnnotationParameter> getParameters() {
        return parameters;
    }

}
