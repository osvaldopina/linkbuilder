package com.github.osvaldopina.linkbuilder.example.extensions.baseuridiscover;


import com.github.osvaldopina.linkbuilder.urigeneration.base.BaseUriDiscover;

public class FixedUrlBaseUriDiscover implements BaseUriDiscover {

    @Override
    public String getBaseUri() {
        return "fixed-url";
    }
}
