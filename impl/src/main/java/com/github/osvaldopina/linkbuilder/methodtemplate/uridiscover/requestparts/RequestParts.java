package com.github.osvaldopina.linkbuilder.methodtemplate.uridiscover.requestparts;

public interface RequestParts {

    String getScheme();

    String getHost();

    int getPort();

    String getContextPath();

    String getUri();
}
