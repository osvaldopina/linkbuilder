package com.github.osvaldopina.linkbuilder.hal.springhateoas;

import static org.easymock.EasyMock.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.lang.reflect.Method;

import com.github.osvaldopina.linkbuilder.LinkProperties;
import com.github.osvaldopina.linkbuilder.expression.ExpressionExecutor;
import com.github.osvaldopina.linkbuilder.fromcall.MethodCall;
import com.github.osvaldopina.linkbuilder.hal.HalLinkProperties;
import com.github.osvaldopina.linkbuilder.urigeneration.link.methodcall.MethodCallUriGenerator;
import com.github.osvaldopina.linkbuilder.utils.IntrospectionUtils;
import org.easymock.EasyMockRule;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.hateoas.ResourceSupport;

// TODO verify the implementation because test is to long and complex!!!!
public class SpringHateoasHalLinkPropertiesLinkCreatorTest extends EasyMockSupport {

	@Rule
	public EasyMockRule mocks = new EasyMockRule(this);

	@Mock
	private MethodCallUriGenerator methodCallUriGenerator;

	@Mock
	private ExpressionExecutor expressionExecutor;

	@Mock
	private IntrospectionUtils introspectionUtils;

	@Mock
	private LinkProperties linkProperties;

	@Mock
	private HalLinkProperties halLinkProperties;

	@Mock
	private MethodCall methodCall;

	@Mock
	private ResourceSupport payload;

	private Object[] params = new Object[]{};

	private HalLinkProperties halLinkPropertiesDummy = new HalLinkProperties(null);

	private Method method = Object.class.getMethods()[0];

	private SpringHateoasHalLinkPropertiesLinkCreator springHateoasHalLinkPropertiesLinkCreator;

	@Before
	public void setUp() {
		springHateoasHalLinkPropertiesLinkCreator =
				new SpringHateoasHalLinkPropertiesLinkCreator(methodCallUriGenerator, expressionExecutor, introspectionUtils);
	}

	@Test
	public void canCreate() throws Exception {
		assertThat(springHateoasHalLinkPropertiesLinkCreator.canCreate(halLinkPropertiesDummy), is(true));
	}

	@Test
	public void canCreate_linkPropertiesIsNotHalLinkProperties() throws Exception {
		assertThat(springHateoasHalLinkPropertiesLinkCreator.canCreate(linkProperties), is(false));
	}

	@Test
	public void create_WhenExpressionIsNullAndHasRel() throws Exception {
		expect(halLinkProperties.getWhenExpression()).andReturn(null);
		expect(halLinkProperties.getMethodCall()).andReturn(methodCall);
		expect(halLinkProperties.getPayload()).andReturn(payload);
		expect(methodCallUriGenerator.generateUri(methodCall, payload)).andReturn("uri");
		expect(halLinkProperties.getRel()).andReturn("rel");
		expect(halLinkProperties.getHreflang()).andReturn("hreflang");

		replayAll();

		Object halLinkObject = springHateoasHalLinkPropertiesLinkCreator.create(halLinkProperties);

		verifyAll();

		assertThat(halLinkObject.getClass(), is(typeCompatibleWith(HalLink.class)));

		HalLink halLink = (HalLink) halLinkObject;

		assertThat(halLink.getRel(), is("rel"));
		assertThat(halLink.getHreflang(), is("hreflang"));
		assertThat(halLink.getHref(), is("uri"));

	}

	@Test
	public void create_WhenExpressionIsNotNullAndHasRel() throws Exception {
		expect(halLinkProperties.getWhenExpression()).andReturn("when-expression");
		expect(halLinkProperties.getPayload()).andReturn(payload);
		expect(halLinkProperties.getMethodCall()).andReturn(methodCall);
		expect(methodCall.getParams()).andReturn(params);
		expect(expressionExecutor.isTrue("when-expression", payload, params)).andReturn(true);
		expect(halLinkProperties.getMethodCall()).andReturn(methodCall);
		expect(halLinkProperties.getPayload()).andReturn(payload);
		expect(methodCallUriGenerator.generateUri(methodCall, payload)).andReturn("uri");
		expect(halLinkProperties.getRel()).andReturn("rel");
		expect(halLinkProperties.getHreflang()).andReturn("hreflang");

		replayAll();

		Object halLinkObject = springHateoasHalLinkPropertiesLinkCreator.create(halLinkProperties);

		verifyAll();

		assertThat(halLinkObject.getClass(), is(typeCompatibleWith(HalLink.class)));

		HalLink halLink = (HalLink) halLinkObject;

		assertThat(halLink.getRel(), is("rel"));
		assertThat(halLink.getHreflang(), is("hreflang"));
		assertThat(halLink.getHref(), is("uri"));

	}

	@Test
	public void create_WhenExpressionIsNullAndHasNoRel() throws Exception {
		expect(halLinkProperties.getWhenExpression()).andReturn(null);
		expect(halLinkProperties.getMethodCall()).andReturn(methodCall);
		expect(halLinkProperties.getPayload()).andReturn(payload);
		expect(methodCallUriGenerator.generateUri(methodCall, payload)).andReturn("uri");
		expect(halLinkProperties.getRel()).andReturn(null);
		expect(halLinkProperties.getMethodCall()).andReturn(methodCall);
		expect(methodCall.getMethod()).andReturn(method);
		expect(introspectionUtils.getMethodRel(method)).andReturn("method-rel");
		expect(halLinkProperties.getHreflang()).andReturn("hreflang");

		replayAll();

		Object halLinkObject = springHateoasHalLinkPropertiesLinkCreator.create(halLinkProperties);

		verifyAll();

		assertThat(halLinkObject.getClass(), is(typeCompatibleWith(HalLink.class)));

		HalLink halLink = (HalLink) halLinkObject;

		assertThat(halLink.getRel(), is("method-rel"));
		assertThat(halLink.getHreflang(), is("hreflang"));
		assertThat(halLink.getHref(), is("uri"));

	}


	@Test
	public void createAndSet() throws Exception {

		payload = new ResourceSupport();
		expect(halLinkProperties.getWhenExpression()).andReturn(null);
		expect(halLinkProperties.getMethodCall()).andReturn(methodCall);
		expect(halLinkProperties.getPayload()).andReturn(payload);
		expect(methodCallUriGenerator.generateUri(methodCall, payload)).andReturn("uri");
		expect(halLinkProperties.getRel()).andReturn("rel");
		expect(halLinkProperties.getHreflang()).andReturn("hreflang");
		expect(halLinkProperties.getPayload()).andReturn(payload);

		replayAll();

		springHateoasHalLinkPropertiesLinkCreator.createAndSet(halLinkProperties);

		verifyAll();

		assertThat(payload.getLinks(), hasSize(1));
		assertThat(payload.getLinks().get(0).getClass(), is(typeCompatibleWith(HalLink.class)));

		HalLink halLink = (HalLink) payload.getLinks().get(0);

		assertThat(halLink.getRel(), is("rel"));
		assertThat(halLink.getHreflang(), is("hreflang"));
		assertThat(halLink.getHref(), is("uri"));

	}
}