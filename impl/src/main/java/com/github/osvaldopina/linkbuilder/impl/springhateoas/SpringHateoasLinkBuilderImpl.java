package com.github.osvaldopina.linkbuilder.impl.springhateoas;

import com.github.osvaldopina.linkbuilder.BaseLinkBuilder;
import com.github.osvaldopina.linkbuilder.LinksBuilder;
import com.github.osvaldopina.linkbuilder.expression.ExpressionExecutor;
import com.github.osvaldopina.linkbuilder.extension.LinkBuilderExtensionFactoryRegistry;
import com.github.osvaldopina.linkbuilder.fromcall.currentcallrecorder.CurrentCallLocator;
import com.github.osvaldopina.linkbuilder.linkcreator.linkbuilder.LinkPropertiesLinkCreators;
import com.github.osvaldopina.linkbuilder.urigeneration.link.methodcall.MethodCallUriGenerator;
import com.github.osvaldopina.linkbuilder.utils.IntrospectionUtils;

public class SpringHateoasLinkBuilderImpl extends BaseLinkBuilder  {


    public SpringHateoasLinkBuilderImpl(
            LinksBuilder linksBuilder,
            CurrentCallLocator currentCallLocator,
            LinkPropertiesLinkCreators linkPropertiesLinkCreators,
            LinkBuilderExtensionFactoryRegistry linkBuilderExtensionFactoryRegistry,
            Object payload) {

        super(linksBuilder,
                currentCallLocator,
                linkPropertiesLinkCreators,
                linkBuilderExtensionFactoryRegistry,
                payload);
    }





}

