package com.github.osvaldopina.linkbuilder.annotation.reader.core;

import static org.easymock.EasyMock.expect;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

import java.lang.annotation.Annotation;

import com.github.osvaldopina.linkbuilder.LinkBuilderException;
import com.github.osvaldopina.linkbuilder.utils.ReflectionUtils;
import com.github.osvaldopina.linkbuilder.utils.Utils;
import org.easymock.EasyMockRule;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Rule;
import org.junit.Test;

public class LinkRelExtractorTest  extends EasyMockSupport {

	@Rule
	public EasyMockRule mocks = new EasyMockRule(this);

	@Mock
	PropertyAsStringExtractor propertyAsStringExtractor;

	@Mock
	ReflectionUtils reflectionUtils;

	@Mock
	Utils utils;

	@Mock
	Annotation annotation;

	String overridedRel = "overridedRel";

	String rel = "rel";

	ToString destination = new ToString();

	@TestSubject
	LinkRelExtractor linkRelExtractor = new LinkRelExtractor();

	@Test
	public void extract_hasOverridedLinkRel() {
		expect(propertyAsStringExtractor.extract(annotation, "overridedRel")).andReturn(overridedRel);

		replayAll();

		assertThat(linkRelExtractor.extract(annotation), is("overridedRel"));

		verifyAll();
	}

	@Test
	public void extract_hasNoOverridedRelButHasRel() {
		expect(propertyAsStringExtractor.extract(annotation, "overridedRel")).andReturn(null);
		expect(propertyAsStringExtractor.extract(annotation, "rel")).andReturn(rel);

		replayAll();

		assertThat(linkRelExtractor.extract(annotation), is("rel"));

		verifyAll();
	}

	@Test
	public void extract_hasNoOverridedRelNoRelAndDestinationHasObjectWithGetRelProperty() {
		expect(propertyAsStringExtractor.extract(annotation, "overridedRel")).andReturn(null);
		expect(propertyAsStringExtractor.extract(annotation, "rel")).andReturn(null);
		expect(reflectionUtils.hasMethod(annotation, "destination")).andReturn(true);
		expect(reflectionUtils.callMethod(Object.class, annotation, "destination")).andReturn(destination);
		expect(reflectionUtils.hasMethod(destination, "getRel")).andReturn(true);
		expect(reflectionUtils.callMethod(Object.class, destination, "getRel")).andReturn(rel);

		replayAll();

		assertThat(linkRelExtractor.extract(annotation), is("rel"));

		verifyAll();
	}

	@Test
	public void extract_hasNoOverridedRelNoRelAndDestinationHasValueAndObjectWithGetRelPropertyWithNullValue() {
		expect(propertyAsStringExtractor.extract(annotation, "overridedRel")).andReturn(null);
		expect(propertyAsStringExtractor.extract(annotation, "rel")).andReturn(null);
		expect(reflectionUtils.hasMethod(annotation, "destination")).andReturn(true);
		expect(reflectionUtils.callMethod(Object.class, annotation, "destination")).andReturn(destination);
		expect(reflectionUtils.hasMethod(destination, "getRel")).andReturn(true);
		expect(reflectionUtils.callMethod(Object.class, destination, "getRel")).andReturn(null);
		expect(utils.isPresent(destination)).andReturn(true);

		replayAll();

		assertThat(linkRelExtractor.extract(annotation), is("ToString-value"));

		verifyAll();
	}

	@Test(expected = LinkBuilderException.class)
	public void extract_hasNoOverridedRelNoRelAndDestinationIsNullAndObjectWithGetRelPropertyWithNullValue() {
		expect(propertyAsStringExtractor.extract(annotation, "overridedRel")).andReturn(null);
		expect(propertyAsStringExtractor.extract(annotation, "rel")).andReturn(null);
		expect(reflectionUtils.hasMethod(annotation, "destination")).andReturn(true);
		expect(reflectionUtils.callMethod(Object.class, annotation, "destination")).andReturn(destination);
		expect(reflectionUtils.hasMethod(destination, "getRel")).andReturn(true);
		expect(reflectionUtils.callMethod(Object.class, destination, "getRel")).andReturn(null);
		expect(utils.isPresent(destination)).andReturn(false);

		replayAll();

		linkRelExtractor.extract(annotation);

		verifyAll();
	}

	static class ToString {

		@Override
		public String toString() {
			return "ToString-value";
		}
	}

}