package com.github.osvaldopina.linkbuilder.urigeneration.base.requestparts;

public interface RequestParts {

    String getScheme();

    String getHost();

    int getPort();

    String getContextPath();

    String getUri();
}
