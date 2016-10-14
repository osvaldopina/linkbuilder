package com.github.osvaldopina.linkbuilder.impl.springhateoas;

import com.github.osvaldopina.linkbuilder.BaseLinkBuilder;
import com.github.osvaldopina.linkbuilder.LinksBuilder;
import com.github.osvaldopina.linkbuilder.expression.ExpressionExecutor;
import com.github.osvaldopina.linkbuilder.fromcall.currentcallrecorder.CurrentCallLocator;
import com.github.osvaldopina.linkbuilder.linkcreator.LinkCreators;
import com.github.osvaldopina.linkbuilder.methodtemplate.urigenerator.MethodCallUriGenerator;

public class SpringHateoasLinkBuilderImpl extends BaseLinkBuilder  {


    public SpringHateoasLinkBuilderImpl(
            LinksBuilder linksBuilder,
            ExpressionExecutor expressionExecutor,
            MethodCallUriGenerator methodCallUriGenerator,
            CurrentCallLocator currentCallLocator,
            LinkCreators linkCreators,
            Object payload) {

        super(linksBuilder, expressionExecutor, methodCallUriGenerator, currentCallLocator, linkCreators, payload);
    }

    public String getRel() {
        return super.getRel();
    }




}

