package com.github.osvaldopina.linkbuilder.annotation.reader.core;

import static org.easymock.EasyMock.expect;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.lang.annotation.Annotation;

import com.github.osvaldopina.linkbuilder.utils.ReflectionUtils;
import com.github.osvaldopina.linkbuilder.utils.Utils;
import org.easymock.EasyMockRule;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Rule;
import org.junit.Test;

public class PropertyAsStringExtractorTest  extends EasyMockSupport {

	@Rule
	public EasyMockRule mocks = new EasyMockRule(this);

	@Mock
	ReflectionUtils reflectionUtils;

	@Mock
	Utils utils;

	@Mock
	Annotation annotation;

	@TestSubject
	PropertyAsStringExtractor propertyAsStringExtractor = new PropertyAsStringExtractor();

	ToString toStringValue = new ToString();

	String propName = "propName";

	@Test
	public void extract() throws Exception {
		expect(reflectionUtils.hasMethod(annotation, propName)).andReturn(true);
		expect(reflectionUtils.callMethod(Object.class,annotation,propName)).andReturn(toStringValue);
		expect(utils.isPresent(toStringValue)).andReturn(true);

		replayAll();

		assertThat(propertyAsStringExtractor.extract(annotation, propName), is("ToString-value"));

		verifyAll();


	}


	@Test
	public void extract_hasNoPropNameMethod() throws Exception {
		expect(reflectionUtils.hasMethod(annotation, propName)).andReturn(false);

		replayAll();

		assertThat(propertyAsStringExtractor.extract(annotation, propName), is(nullValue()));

		verifyAll();


	}

	@Test
	public void extract_valueNotPresent() throws Exception {
		expect(reflectionUtils.hasMethod(annotation, propName)).andReturn(true);
		expect(reflectionUtils.callMethod(Object.class,annotation,propName)).andReturn(toStringValue);
		expect(utils.isPresent(toStringValue)).andReturn(false);

		replayAll();

		assertThat(propertyAsStringExtractor.extract(annotation, propName), is(nullValue()));

		verifyAll();


	}


	static class ToString {

		@Override
		public String toString() {
			return "ToString-value";
		}
	}
}