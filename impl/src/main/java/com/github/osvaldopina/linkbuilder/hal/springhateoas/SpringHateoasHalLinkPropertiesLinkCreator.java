package com.github.osvaldopina.linkbuilder.hal.springhateoas;

import com.github.osvaldopina.linkbuilder.LinkProperties;
import com.github.osvaldopina.linkbuilder.expression.ExpressionExecutor;
import com.github.osvaldopina.linkbuilder.hal.HalLinkProperties;
import com.github.osvaldopina.linkbuilder.hal.springhateoas.HalLink;
import com.github.osvaldopina.linkbuilder.linkcreator.linkbuilder.LinkPropertiesLinkCreator;
import com.github.osvaldopina.linkbuilder.urigeneration.link.methodcall.MethodCallUriGenerator;
import com.github.osvaldopina.linkbuilder.utils.IntrospectionUtils;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.util.Assert;

public class SpringHateoasHalLinkPropertiesLinkCreator implements LinkPropertiesLinkCreator {


    private MethodCallUriGenerator methodCallUriGenerator;

    private ExpressionExecutor expressionExecutor;

    private IntrospectionUtils introspectionUtils;

    public SpringHateoasHalLinkPropertiesLinkCreator(
            MethodCallUriGenerator methodCallUriGenerator,
            ExpressionExecutor expressionExecutor,
            IntrospectionUtils introspectionUtils) {

        this.methodCallUriGenerator = methodCallUriGenerator;
        this.expressionExecutor = expressionExecutor;
        this.introspectionUtils = introspectionUtils;
    }

    @Override
    public boolean canCreate(LinkProperties linkProperties) {
        return HalLinkProperties.class == linkProperties.getClass();
    }

    @Override
    public Object create(LinkProperties linkProperties) {

        if (linkProperties.getWhenExpression() == null ||
                expressionExecutor.isTrue(
                        linkProperties.getWhenExpression(),
                        linkProperties.getPayload(),
                        linkProperties.getMethodCall().getParams()
                )) {
            String uri = methodCallUriGenerator.generateUri(linkProperties.getMethodCall(), linkProperties.getPayload());
            String rel = linkProperties.getRel();
            if (linkProperties.getRel() == null) {
                rel = introspectionUtils.getMethodRel(linkProperties.getMethodCall().getMethod());
            }
            return new HalLink(uri, rel,((HalLinkProperties) linkProperties).getHreflang());
        } else {
            return null;
        }
    }


    @Override
    public void createAndSet(LinkProperties linkProperties) {
        Assert.notNull(linkProperties.getPayload());
        Assert.isAssignable(ResourceSupport.class, linkProperties.getPayload().getClass(), "payload must be a subclass of "
                + ResourceSupport.class);

        HalLink link = (HalLink) create(linkProperties);
        if (link != null) {
            ResourceSupport resourceSupport = (ResourceSupport) linkProperties.getPayload();
            resourceSupport.add(link);
        }
    }
}
