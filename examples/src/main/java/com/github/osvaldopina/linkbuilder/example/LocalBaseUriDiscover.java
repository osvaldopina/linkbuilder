package com.github.osvaldopina.linkbuilder.example;

import com.github.osvaldopina.linkbuilder.methodtemplate.uridiscover.BaseUriDiscover;

public class LocalBaseUriDiscover implements BaseUriDiscover {
    @Override
    public String getBaseUri() {
        return "base-url";
    }
}
