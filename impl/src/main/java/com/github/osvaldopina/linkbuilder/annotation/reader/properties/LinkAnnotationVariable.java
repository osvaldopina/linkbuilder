package com.github.osvaldopina.linkbuilder.annotation.reader.properties;

public class LinkAnnotationVariable {

    private String name;

    private String value;

    private String when;

    public LinkAnnotationVariable(String name, String value, String when) {
        this.name = name;
        this.value = value;
        this.when = when;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public String getWhen() {
        return when;
    }
}
