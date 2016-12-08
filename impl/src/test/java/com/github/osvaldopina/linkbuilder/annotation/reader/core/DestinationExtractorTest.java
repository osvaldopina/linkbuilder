package com.github.osvaldopina.linkbuilder.annotation.reader.core;

import static org.easymock.EasyMock.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.lang.annotation.Annotation;

import com.github.osvaldopina.linkbuilder.LinkBuilderException;
import com.github.osvaldopina.linkbuilder.linkdestination.springhateoas.DestinationIdentityFactorty;
import com.github.osvaldopina.linkbuilder.utils.ReflectionUtils;
import com.github.osvaldopina.linkbuilder.utils.Utils;
import org.easymock.EasyMockRule;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Rule;
import org.junit.Test;

public class DestinationExtractorTest extends EasyMockSupport {

	@Rule
	public EasyMockRule mocks = new EasyMockRule(this);

	@Mock
	ReflectionUtils reflectionUtils;

	@Mock
	Utils utils;

	@Mock
	Annotation annotation;

	@Mock
	DestinationIdentityFactorty destinationIdentityFactorty;

	ToString destination = new ToString();

	Class<?> controller = Object.class;

	String rel = "rel";

	@TestSubject
	DestinationExtractor destinationExtractor = new DestinationExtractor();


	@Test
	public void extract_annotationHasDestination() throws Exception {

		expect(reflectionUtils.hasValue(annotation, "destination")).andReturn(true);
		expect(reflectionUtils.hasValue(annotation, "controller")).andReturn(false);
		expect(reflectionUtils.hasValue(annotation, "rel")).andReturn(false);
		expect(reflectionUtils.callMethod(Object.class, annotation, "destination")).andReturn(destination);

		replayAll();

		assertThat(destinationExtractor.extract(annotation), is("destination-to-string"));

		verifyAll();
	}

	@Test(expected = LinkBuilderException.class)
	public void extract_annotationHasDestinationAndController() throws Exception {

		expect(reflectionUtils.hasValue(annotation, "destination")).andReturn(true);
		expect(reflectionUtils.hasValue(annotation, "controller")).andReturn(true);

		replayAll();

		assertThat(destinationExtractor.extract(annotation), is("destination-to-string"));

		verifyAll();
	}

	@Test(expected = LinkBuilderException.class)
	public void extract_annotationHasDestinationAndRel() throws Exception {

		expect(reflectionUtils.hasValue(annotation, "destination")).andReturn(true);
		expect(reflectionUtils.hasValue(annotation, "controller")).andReturn(false);
		expect(reflectionUtils.hasValue(annotation, "rel")).andReturn(true);

		replayAll();

		assertThat(destinationExtractor.extract(annotation), is("destination-to-string"));

		verifyAll();
	}

	@Test
	public void extract_annotationHasNotDestinationButHasControllerAndRel() throws Exception {

		expect(reflectionUtils.hasValue(annotation, "destination")).andReturn(false);
		expect(reflectionUtils.hasValue(annotation, "controller")).andReturn(true);
		expect(reflectionUtils.hasValue(annotation, "rel")).andReturn(true);
		expect(reflectionUtils.callMethod(Class.class, annotation, "controller")).andReturn(controller);
		expect(reflectionUtils.callMethod(String.class, annotation, "rel")).andReturn(rel);
		expect(destinationIdentityFactorty.destination(controller, rel)).andReturn("destination");

		replayAll();

		assertThat(destinationExtractor.extract(annotation), is("destination"));

		verifyAll();
	}

	@Test(expected = LinkBuilderException.class)
	public void extract_annotationHasNotDestinationHasControllerAndNotRel() throws Exception {

		expect(reflectionUtils.hasValue(annotation, "destination")).andReturn(false);
		expect(reflectionUtils.hasValue(annotation, "controller")).andReturn(true);
		expect(reflectionUtils.hasValue(annotation, "rel")).andReturn(false);

		replayAll();

		assertThat(destinationExtractor.extract(annotation), is("destination"));

		verifyAll();
	}

	@Test(expected = LinkBuilderException.class)
	public void extract_annotationHasNotDestinationAndNoController() throws Exception {

		expect(reflectionUtils.hasValue(annotation, "destination")).andReturn(false);
		expect(reflectionUtils.hasValue(annotation, "controller")).andReturn(false);

		replayAll();

		assertThat(destinationExtractor.extract(annotation), is("destination"));

		verifyAll();
	}

	public static class ToString {

		@Override
		public String toString() {
			return "destination-to-string";
		}
	}
}