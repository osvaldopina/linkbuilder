package com.github.osvaldopina.linkbuilder.hal.annotation.reader;

import static org.easymock.EasyMock.expect;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;


import com.github.osvaldopina.linkbuilder.LinkProperties;
import com.github.osvaldopina.linkbuilder.annotation.reader.core.DestinationExtractor;
import com.github.osvaldopina.linkbuilder.annotation.reader.core.LinkRelExtractor;
import com.github.osvaldopina.linkbuilder.annotation.reader.properties.LinkAnnotationParameter;
import com.github.osvaldopina.linkbuilder.annotation.reader.properties.LinkAnnotationProperties;
import com.github.osvaldopina.linkbuilder.hal.annotation.Param;
import com.github.osvaldopina.linkbuilder.hal.annotation.reader.properties.HalLinkAnnotationProperties;
import com.github.osvaldopina.linkbuilder.urigeneration.base.BaseUriDiscover;
import com.github.osvaldopina.linkbuilder.utils.ReflectionUtils;
import org.easymock.EasyMockRule;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Rule;
import org.junit.Test;

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
	private Param linkParam;

	@TestSubject
	HalLinkAnnotationReader halLinkAnnotationReader = new HalLinkAnnotationReader();


	@Test
	public void read() {
		expect(halLinkAnnotationRetriever.getLinksAnnotation(method.getDeclaredAnnotations())).andReturn(linksAnnotation);
		expect(reflectionUtils.callMethod(Annotation[].class, linksAnnotation, "value")).
				andReturn(new Annotation[]{linkAnnotation});
		expect(destinationExtractor.extract(linkAnnotation)).andReturn("destintaion");
		expect(linkRelExtractor.extract(linkAnnotation)).andReturn("rel");
		expect(reflectionUtils.callMethod(boolean.class, linkAnnotation, "templated")).andReturn(true);
		expect(reflectionUtils.callMethod(String.class, linkAnnotation, "hreflang")).andReturn("hreflang");
		// link params
		expect(reflectionUtils.callMethod(Param[].class, linkAnnotation, "params")).andReturn(new Param[]{linkParam});
		expect(linkParam.name()).andReturn("param-name");
		expect(linkParam.value()).andReturn("param-value");
		expect(linkParam.when()).andReturn("param-when");

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

		LinkAnnotationParameter linkAnnotationParameter = halLinkAnnotationProperties.getParameters().get(0);
		assertThat(linkAnnotationParameter.getName(), is("param-name"));
		assertThat(linkAnnotationParameter.getValue(), is("param-value"));
		assertThat(linkAnnotationParameter.getWhen(), is("param-when"));

		verifyAll();
	}

	@Test
	public void read_noParameters() {
		expect(halLinkAnnotationRetriever.getLinksAnnotation(method.getDeclaredAnnotations())).andReturn(linksAnnotation);
		expect(reflectionUtils.callMethod(Annotation[].class, linksAnnotation, "value")).
				andReturn(new Annotation[]{linkAnnotation});
		expect(destinationExtractor.extract(linkAnnotation)).andReturn("destintaion");
		expect(linkRelExtractor.extract(linkAnnotation)).andReturn("rel");
		expect(reflectionUtils.callMethod(boolean.class, linkAnnotation, "templated")).andReturn(true);
		expect(reflectionUtils.callMethod(String.class, linkAnnotation, "hreflang")).andReturn("hreflang");
		// link params
		expect(reflectionUtils.callMethod(Param[].class, linkAnnotation, "params")).andReturn(new Param[]{});

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
		expect(halLinkAnnotationRetriever.getLinksAnnotation(method.getDeclaredAnnotations())).andReturn(linksAnnotation);

		replayAll();

		assertThat(halLinkAnnotationReader.canRead(method), is(true));

		verifyAll();
	}

	@Test
	public void canRead_nonAnnotatedMethod() {
		expect(halLinkAnnotationRetriever.getLinksAnnotation(method.getDeclaredAnnotations())).andReturn(null);

		replayAll();

		assertThat(halLinkAnnotationReader.canRead(method), is(false));

		verifyAll();
	}

}