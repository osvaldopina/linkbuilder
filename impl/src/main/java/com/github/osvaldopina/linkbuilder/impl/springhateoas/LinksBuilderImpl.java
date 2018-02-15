package com.github.osvaldopina.linkbuilder.impl.springhateoas;


import com.github.osvaldopina.linkbuilder.LinkBuilder;
import com.github.osvaldopina.linkbuilder.LinksBuilder;
import com.github.osvaldopina.linkbuilder.expression.ExpressionExecutor;
import com.github.osvaldopina.linkbuilder.linkcreator.LinkCreators;
import com.github.osvaldopina.linkbuilder.methodtemplate.urigenerator.MethodCallUriGenerator;
import com.github.osvaldopina.linkbuilder.utils.IntrospectionUtils;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.List;

/**
 * Builder for Links. This builder should be used to create a list of <code>Link</code>. The method <code>link</code>
 * should be used to create a builder for a link.
 */
class LinksBuilderImpl implements LinksBuilder {

    private Object payload;

    private List<SpringHateoasLinkBuilderImpl> linkBuilders = new ArrayList<SpringHateoasLinkBuilderImpl>();

    private ApplicationContext applicationContext;

    protected LinksBuilderImpl(ApplicationContext applicationContext) {
        this(applicationContext, null);
    }

    protected LinksBuilderImpl(ApplicationContext applicationContext, Object payload) {
        this.applicationContext = applicationContext;
        this.payload = payload;
    }

    @Override
    public LinkBuilder link() {
        ExpressionExecutor expressionExecutor = applicationContext.getBean(ExpressionExecutor.class);
        MethodCallUriGenerator methodCallUriGenerator = applicationContext.getBean(MethodCallUriGenerator.class);
        LinkCreators linkCreators = applicationContext.getBean(LinkCreators.class);
        IntrospectionUtils introspectionUtils = applicationContext.getBean(IntrospectionUtils.class);

        SpringHateoasLinkBuilderImpl linkBuilder = new SpringHateoasLinkBuilderImpl(
                this,
                expressionExecutor,
                methodCallUriGenerator,
                linkCreators,
                payload,
                introspectionUtils
        );
        linkBuilders.add(linkBuilder);
        return linkBuilder;
    }

    @Override
    public void buildAndSetAll() {

        for(LinkBuilder linkBuilder: linkBuilders) {
            if (linkBuilder.whenExpressionIsTrue()) {
                linkBuilder.builAndSet();
            }
        }
    }

}