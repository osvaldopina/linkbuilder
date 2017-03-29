package com.github.osvaldopina.linkbuilder.annotation.reader.properties;

import java.util.ArrayList;
import java.util.List;

public class LinkAnnotationProperties {

    private String destination;

    private String rel;

    private String when;

    private boolean templated;

    private List<LinkAnnotationVariable> parameters = new ArrayList<LinkAnnotationVariable>();

    public LinkAnnotationProperties(String destination, String rel, String when, boolean templated, List<LinkAnnotationVariable> parameters) {
        this.destination = destination;
        this.rel = rel;
        this.when = when;
        this.templated = templated;
        this.parameters.addAll(parameters);
    }


    public String getDestination() {
        return destination;
    }

    public String getRel() {
        return rel;
    }

    public String getWhen() {
        return when;
    }

    public boolean isTemplated() {
        return templated;
    }

    public List<LinkAnnotationVariable> getParameters() {
        return parameters;
    }

}
