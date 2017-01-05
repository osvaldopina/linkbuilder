package com.github.osvaldopina.linkbuilder.hal.springhateoas;

import com.github.osvaldopina.linkbuilder.LinkProperties;
import com.github.osvaldopina.linkbuilder.expression.ExpressionExecutor;
import com.github.osvaldopina.linkbuilder.hal.HalLinkProperties;
import com.github.osvaldopina.linkbuilder.linkcreator.linkbuilder.LinkPropertiesLinkCreator;
import com.github.osvaldopina.linkbuilder.urigeneration.link.methodcall.MethodCallUriGenerator;
import com.github.osvaldopina.linkbuilder.utils.IntrospectionUtils;
import org.springframework.hateoas.ResourceSupport;

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

		String whenExpression = linkProperties.getWhenExpression();
		if (whenExpression == null ||
				expressionExecutor.isTrue(
						whenExpression,
						linkProperties.getResource(),
						linkProperties.getMethodCall().getParams())) {

			String uri = methodCallUriGenerator.generateUri(linkProperties.getMethodCall(), linkProperties.getResource());
			String rel = linkProperties.getRel();
			if (rel == null) {
				rel = introspectionUtils.getMethodRel(linkProperties.getMethodCall().getMethod());
			}
			return new HalLink(uri, rel, ((HalLinkProperties) linkProperties).getHreflang());
		} else {
			return null;
		}
	}


	@Override
	public void createAndSet(LinkProperties linkProperties) {

		HalLink link = (HalLink) create(linkProperties);
		if (link != null) {
			ResourceSupport resourceSupport = (ResourceSupport) linkProperties.getResource();
			resourceSupport.add(link);
		}
	}
}
