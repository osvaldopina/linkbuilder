package com.github.osvaldopina.linkbuilder.impl;

import com.github.osvaldopina.linkbuilder.BaseLinkBuilder;
import com.github.osvaldopina.linkbuilder.LinksBuilder;
import com.github.osvaldopina.linkbuilder.expression.ExpressionExecutor;
import com.github.osvaldopina.linkbuilder.fromcall.currentcallrecorder.CurrentCallLocator;
import com.github.osvaldopina.linkbuilder.methodtemplate.urigenerator.MethodCallUriGenerator;
import org.springframework.hateoas.Link;

public class SpringHateoasLinkBuilderImpl extends BaseLinkBuilder<Link>  {


    public SpringHateoasLinkBuilderImpl(
            LinksBuilder linksBuilder,
            ExpressionExecutor expressionExecutor,
            MethodCallUriGenerator methodCallUriGenerator,
            CurrentCallLocator currentCallLocator,
            Object payload) {

        super(linksBuilder, expressionExecutor, methodCallUriGenerator, currentCallLocator, payload);
    }

    @Override
    protected Link createLink(String uri) {
        return new Link(uri, getRel());
    }


}

