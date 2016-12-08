package com.github.osvaldopina.linkbuilder.annotation.reader.properties;

public class LinkAnnotationParameter {

    private String name;

    private String expression;

    private String when;

    public LinkAnnotationParameter(String name, String expression, String when) {
        this.name = name;
        this.expression = expression;
        this.when = when;
    }

    public String getName() {
        return name;
    }

    public String getExpression() {
        return expression;
    }

    public String getWhen() {
        return when;
    }
}
