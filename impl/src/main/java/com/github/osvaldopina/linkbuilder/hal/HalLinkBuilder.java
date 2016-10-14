package com.github.osvaldopina.linkbuilder.hal;

import com.github.osvaldopina.linkbuilder.BaseLinkBuilder;
import com.github.osvaldopina.linkbuilder.LinkBuilder;
import com.github.osvaldopina.linkbuilder.LinksBuilder;
import com.github.osvaldopina.linkbuilder.expression.ExpressionExecutor;
import com.github.osvaldopina.linkbuilder.fromcall.currentcallrecorder.CurrentCallLocator;
import com.github.osvaldopina.linkbuilder.methodtemplate.urigenerator.MethodCallUriGenerator;

public class HalLinkBuilder extends BaseLinkBuilder<HalLink> {

    private String type;

    private String profile;

    private String title;

    private String hrefLang;

    public HalLinkBuilder(
            LinksBuilder linksBuilder,
            ExpressionExecutor expressionExecutor,
            MethodCallUriGenerator methodCallUriGenerator,
            CurrentCallLocator currentCallLocator,
            Object payload) {
        super(linksBuilder, expressionExecutor, methodCallUriGenerator, currentCallLocator, payload);
    }

    public HalLinkBuilder type(String type) {
        this.type = type;
        return this;
    }

    public HalLinkBuilder profile(String profile) {
        this.profile = profile;
        return this;
    }

    public HalLinkBuilder title(String title) {
        this.title = title;
        return this;
    }

    public HalLinkBuilder hrefLang(String hrefLang) {
        this.hrefLang = hrefLang;
        return this;
    }

    @Override
    protected HalLink createLink(String uri) {
        return new HalLink(uri, getRel(), type, profile, title, hrefLang);
    }
}
