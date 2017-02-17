package com.github.osvaldopina.linkbuilder.linkcreator.linkbuilder.impl;

import static org.easymock.EasyMock.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.lang.reflect.Method;

import com.github.osvaldopina.linkbuilder.BaseLinkProperties;
import com.github.osvaldopina.linkbuilder.LinkProperties;
import com.github.osvaldopina.linkbuilder.expression.ExpressionExecutor;
import com.github.osvaldopina.linkbuilder.fromcall.MethodCall;
import com.github.osvaldopina.linkbuilder.template.conditionalsubustitution.ConditionalVariableSubstitutionStrategies;
import com.github.osvaldopina.linkbuilder.urigeneration.link.methodcall.MethodCallUriGenerator;
import com.github.osvaldopina.linkbuilder.utils.IntrospectionUtils;
import org.easymock.EasyMockRule;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;

public class SpringHateoasLinkPropertiesLinkCreatorTest extends EasyMockSupport {

	@Rule
	public EasyMockRule mocks = new EasyMockRule(this);

	@Mock
	private MethodCallUriGenerator methodCallUriGenerator;

	@Mock
	private ExpressionExecutor expressionExecutor;

	@Mock
	private IntrospectionUtils introspectionUtils;

	@Mock
	private ConditionalVariableSubstitutionStrategies conditionalVariableSubstitutionStrategies;

	private BaseLinkProperties baseLinkProperties = new BaseLinkProperties();

	@Mock
	private LinkProperties linkProperties;

	@Mock
	private MethodCall methodCall;


	private SpringHateoasLinkPropertiesLinkCreator springHateoasLinkPropertiesLinkCreator;

	private ResourceSupport resourceAsRessourceSupport;

	private Method method = Object.class.getMethods()[0];

	private Object[] params = new Object[]{};

	@Before
	public void setUp() {

		resourceAsRessourceSupport = new ResourceSupport();

		springHateoasLinkPropertiesLinkCreator =
				new SpringHateoasLinkPropertiesLinkCreator(methodCallUriGenerator, expressionExecutor, introspectionUtils);
	}

	@Test
	public void canCreate() {

		replayAll();

		assertThat(springHateoasLinkPropertiesLinkCreator.canCreate(baseLinkProperties), is(true));

		verifyAll();
	}

	@Test
	public void canCreateLinkPropertiesIsNotBaseLinkProperties() {

		replayAll();

		assertThat(springHateoasLinkPropertiesLinkCreator.canCreate(linkProperties), is(false));

		verifyAll();
	}

	@Test
	public void createWhenExpressionIsNullAndLinkPropertiesHasRel() {

		expect(linkProperties.getWhenExpression()).andReturn(null);
		expect(linkProperties.getMethodCall()).andReturn(methodCall);
		expect(linkProperties.getResource()).andReturn(resourceAsRessourceSupport);
		expect(linkProperties.getConditionalVariableSubstituionStrategies()).andReturn(conditionalVariableSubstitutionStrategies);
		expect(linkProperties.isTemplated()).andReturn(true);
		expect(methodCallUriGenerator.generateUri(methodCall, resourceAsRessourceSupport,
				conditionalVariableSubstitutionStrategies, true)).andReturn("controller-uri");
		expect(linkProperties.getRel()).andReturn("rel");

		replayAll();

		Object link = springHateoasLinkPropertiesLinkCreator.create(linkProperties);

		assertThat(link.getClass(), is(typeCompatibleWith(Link.class)));
		Link hateoasLink = (Link) link;
		assertThat(hateoasLink.getHref(), is("controller-uri"));
		assertThat(hateoasLink.getRel(), is("rel"));

		verifyAll();
	}

	@Test
	public void createWhenExpressionIsNullAndLinkPropertiesHasNoRel() {

		expect(linkProperties.getWhenExpression()).andReturn(null);
		expect(linkProperties.getMethodCall()).andReturn(methodCall);
		expect(linkProperties.getResource()).andReturn(resourceAsRessourceSupport);
		expect(linkProperties.getConditionalVariableSubstituionStrategies()).andReturn(conditionalVariableSubstitutionStrategies);
		expect(linkProperties.isTemplated()).andReturn(true);
		expect(methodCallUriGenerator.generateUri(methodCall, resourceAsRessourceSupport,conditionalVariableSubstitutionStrategies,
				true)).andReturn("controller-uri");
		expect(linkProperties.getRel()).andReturn(null);
		expect(linkProperties.getMethodCall()).andReturn(methodCall);
		expect(methodCall.getMethod()).andReturn(method);
		expect(introspectionUtils.getMethodRel(method)).andReturn("rel");

		replayAll();

		Object link = springHateoasLinkPropertiesLinkCreator.create(linkProperties);

		assertThat(link.getClass(), is(typeCompatibleWith(Link.class)));
		Link hateoasLink = (Link) link;
		assertThat(hateoasLink.getHref(), is("controller-uri"));
		assertThat(hateoasLink.getRel(), is("rel"));

		verifyAll();
	}

	@Test
	public void createWhenExpressionIsTrueAndLinkPropertiesHasRel() {

		expect(linkProperties.getWhenExpression()).andReturn("expression");
		expect(linkProperties.getResource()).andReturn(resourceAsRessourceSupport);
		expect(linkProperties.getMethodCall()).andReturn(methodCall);
		expect(methodCall.getParams()).andReturn(params);
		expect(expressionExecutor.isTrue("expression", resourceAsRessourceSupport, params)).andReturn(true);
		expect(linkProperties.getMethodCall()).andReturn(methodCall);
		expect(linkProperties.getResource()).andReturn(resourceAsRessourceSupport);
		expect(linkProperties.isTemplated()).andReturn(true);
		expect(linkProperties.getConditionalVariableSubstituionStrategies()).andReturn(conditionalVariableSubstitutionStrategies);
		expect(methodCallUriGenerator.generateUri(methodCall, resourceAsRessourceSupport,conditionalVariableSubstitutionStrategies,
				true)).andReturn("controller-uri");
		expect(linkProperties.getRel()).andReturn("rel");

		replayAll();

		Object link = springHateoasLinkPropertiesLinkCreator.create(linkProperties);

		assertThat(link.getClass(), is(typeCompatibleWith(Link.class)));
		Link hateoasLink = (Link) link;
		assertThat(hateoasLink.getHref(), is("controller-uri"));
		assertThat(hateoasLink.getRel(), is("rel"));

		verifyAll();
	}

	@Test
	public void createWhenExpressionIsFalse() {

		expect(linkProperties.getWhenExpression()).andReturn("expression");
		expect(linkProperties.getResource()).andReturn(resourceAsRessourceSupport);
		expect(linkProperties.getMethodCall()).andReturn(methodCall);
		expect(methodCall.getParams()).andReturn(params);
		expect(expressionExecutor.isTrue("expression", resourceAsRessourceSupport, params)).andReturn(false);

		replayAll();

		assertThat(springHateoasLinkPropertiesLinkCreator.create(linkProperties), is(nullValue()));

		verifyAll();
	}

	@Test
	public void createAndSetLinkIsNotNull() {

		expect(linkProperties.getWhenExpression()).andReturn(null);
		expect(linkProperties.getMethodCall()).andReturn(methodCall);
		expect(linkProperties.getResource()).andReturn(resourceAsRessourceSupport);
		expect(linkProperties.isTemplated()).andReturn(true);
		expect(linkProperties.getConditionalVariableSubstituionStrategies()).andReturn(conditionalVariableSubstitutionStrategies);
		expect(methodCallUriGenerator.generateUri(methodCall, resourceAsRessourceSupport,conditionalVariableSubstitutionStrategies,
				true)).andReturn("controller-uri");
		expect(linkProperties.getRel()).andReturn("rel");
		expect(linkProperties.getResource()).andReturn(resourceAsRessourceSupport);

		replayAll();

		springHateoasLinkPropertiesLinkCreator.createAndSet(linkProperties);

		assertThat(resourceAsRessourceSupport.getLinks(), hasSize(1));
		Link link = resourceAsRessourceSupport.getLinks().get(0);
		assertThat(link.getHref(), is("controller-uri"));
		assertThat(link.getRel(), is("rel"));

		verifyAll();
	}

	@Test
	public void createAndSetLinkIsNull() {

		expect(linkProperties.getWhenExpression()).andReturn("expression");
		expect(linkProperties.getResource()).andReturn(resourceAsRessourceSupport);
		expect(linkProperties.getMethodCall()).andReturn(methodCall);
		expect(methodCall.getParams()).andReturn(params);
		expect(expressionExecutor.isTrue("expression", resourceAsRessourceSupport, params)).andReturn(false);

		replayAll();

		springHateoasLinkPropertiesLinkCreator.createAndSet(linkProperties);

		assertThat(resourceAsRessourceSupport.getLinks(), hasSize(0));

		verifyAll();
	}
}

