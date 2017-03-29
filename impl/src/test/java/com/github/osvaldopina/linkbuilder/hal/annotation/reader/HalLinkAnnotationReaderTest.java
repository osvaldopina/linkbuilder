package com.github.osvaldopina.linkbuilder.hal.annotation.reader;

import static org.easymock.EasyMock.expect;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;


import com.github.osvaldopina.linkbuilder.annotation.reader.core.DestinationExtractor;
import com.github.osvaldopina.linkbuilder.annotation.reader.core.LinkRelExtractor;
import com.github.osvaldopina.linkbuilder.annotation.reader.properties.LinkAnnotationVariable;
import com.github.osvaldopina.linkbuilder.annotation.reader.properties.LinkAnnotationProperties;
import com.github.osvaldopina.linkbuilder.hal.annotation.Variable;
import com.github.osvaldopina.linkbuilder.hal.annotation.reader.properties.HalLinkAnnotationProperties;
import com.github.osvaldopina.linkbuilder.utils.ReflectionUtils;
import org.easymock.EasyMockRule;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;

@Ignore
// todo reimplement
public class HalLinkAnnotationReaderTest extends EasyMockSupport {

	@Rule
	public EasyMockRule mocks = new EasyMockRule(this);

	@Mock
	private LinkRelExtractor linkRelExtractor;

	@Mock
	private DestinationExtractor destinationExtractor;

	@Mock
	private ReflectionUtils reflectionUtils;

	@Mock
	private HalLinkAnnotationRetriever halLinkAnnotationRetriever;

	private Method method = Object.class.getMethods()[0];

	@Mock
	private Annotation linksAnnotation;

	@Mock
	private Annotation linkAnnotation;

	@Mock
	private Variable linkVariable;

	@TestSubject
	HalLinkAnnotationReader halLinkAnnotationReader = new HalLinkAnnotationReader();


	@Test
	public void read() {
		// TODO acertar
	//	expect(halLinkAnnotationRetriever.getLinksAnnotation(method.getDeclaredAnnotations())).andReturn(linksAnnotation);
		expect(reflectionUtils.callMethod(Annotation[].class, linksAnnotation, "value")).
				andReturn(new Annotation[]{linkAnnotation});
		expect(destinationExtractor.extract(linkAnnotation)).andReturn("destintaion");
		expect(linkRelExtractor.extract(linkAnnotation)).andReturn("rel");
		expect(reflectionUtils.callMethod(boolean.class, linkAnnotation, "templated")).andReturn(true);
		expect(reflectionUtils.callMethod(String.class, linkAnnotation, "hreflang")).andReturn("hreflang");
		// link variables
		expect(reflectionUtils.callMethod(Variable[].class, linkAnnotation, "variables")).andReturn(new Variable[]{linkVariable});
		expect(linkVariable.name()).andReturn("param-name");
		expect(linkVariable.value()).andReturn("param-value");
		expect(linkVariable.when()).andReturn("param-when");

		replayAll();

		List<LinkAnnotationProperties> linkPropertiesList = halLinkAnnotationReader.read(method);

		assertThat(linkPropertiesList, hasSize(1));

		LinkAnnotationProperties linkProperties = linkPropertiesList.get(0);
		assertThat(linkProperties.getClass(), is(typeCompatibleWith(HalLinkAnnotationProperties.class)));

		HalLinkAnnotationProperties halLinkAnnotationProperties = (HalLinkAnnotationProperties) linkProperties;
		assertThat(halLinkAnnotationProperties.getRel(), is("rel"));
		assertThat(halLinkAnnotationProperties.isTemplated(), is(true));
		assertThat(halLinkAnnotationProperties.getHreflang(), is("hreflang"));
		assertThat(halLinkAnnotationProperties.getParameters(), hasSize(1));

		LinkAnnotationVariable linkAnnotationVariable = halLinkAnnotationProperties.getParameters().get(0);
		assertThat(linkAnnotationVariable.getName(), is("param-name"));
		assertThat(linkAnnotationVariable.getValue(), is("param-value"));
		assertThat(linkAnnotationVariable.getWhen(), is("param-when"));

		verifyAll();
	}

	@Test
	public void read_noParameters() {
		// TODO acertar
//		expect(halLinkAnnotationRetriever.getLinksAnnotation(method.getDeclaredAnnotations())).andReturn(linksAnnotation);
		expect(reflectionUtils.callMethod(Annotation[].class, linksAnnotation, "value")).
				andReturn(new Annotation[]{linkAnnotation});
		expect(destinationExtractor.extract(linkAnnotation)).andReturn("destintaion");
		expect(linkRelExtractor.extract(linkAnnotation)).andReturn("rel");
		expect(reflectionUtils.callMethod(boolean.class, linkAnnotation, "templated")).andReturn(true);
		expect(reflectionUtils.callMethod(String.class, linkAnnotation, "hreflang")).andReturn("hreflang");
		// link variables
		expect(reflectionUtils.callMethod(Variable[].class, linkAnnotation, "variables")).andReturn(new Variable[]{});

		replayAll();

		List<LinkAnnotationProperties> linkPropertiesList = halLinkAnnotationReader.read(method);

		assertThat(linkPropertiesList, hasSize(1));

		LinkAnnotationProperties linkProperties = linkPropertiesList.get(0);
		assertThat(linkProperties.getClass(), is(typeCompatibleWith(HalLinkAnnotationProperties.class)));

		HalLinkAnnotationProperties halLinkAnnotationProperties = (HalLinkAnnotationProperties) linkProperties;
		assertThat(halLinkAnnotationProperties.getRel(), is("rel"));
		assertThat(halLinkAnnotationProperties.isTemplated(), is(true));
		assertThat(halLinkAnnotationProperties.getHreflang(), is("hreflang"));
		assertThat(halLinkAnnotationProperties.getParameters(), hasSize(0));

		verifyAll();
	}

	@Test
	public void canRead_annotatedMethod() {
		// TODO acertar
//		expect(halLinkAnnotationRetriever.getLinksAnnotation(method.getDeclaredAnnotations())).andReturn(linksAnnotation);

		replayAll();

		assertThat(halLinkAnnotationReader.canRead(method), is(true));

		verifyAll();
	}

	@Test
	public void canRead_nonAnnotatedMethod() {
		// TODO acertar
//		expect(halLinkAnnotationRetriever.getLinksAnnotation(method.getDeclaredAnnotations())).andReturn(null);

		replayAll();

		assertThat(halLinkAnnotationReader.canRead(method), is(false));

		verifyAll();
	}

}