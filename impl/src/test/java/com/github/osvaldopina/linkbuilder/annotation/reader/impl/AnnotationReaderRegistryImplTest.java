package com.github.osvaldopina.linkbuilder.annotation.reader.impl;

import static org.easymock.EasyMock.expect;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

import java.lang.reflect.Method;
import java.util.Arrays;

import com.github.osvaldopina.linkbuilder.annotation.reader.AnnotationReader;
import com.github.osvaldopina.linkbuilder.utils.Utils;
import org.easymock.EasyMockRule;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class AnnotationReaderRegistryImplTest extends EasyMockSupport {

	@Rule
	public EasyMockRule mocks = new EasyMockRule(this);

	@Mock
	AnnotationReader annotationReader;

	@Mock
	Utils utils;

	Method method = Object.class.getMethod("toString");

	AnnotationReaderRegistryImpl annotationReaderRegistryImpl;

	public AnnotationReaderRegistryImplTest() throws Exception {
	}

	@Before
	public void setUp() {
		annotationReaderRegistryImpl = new AnnotationReaderRegistryImpl(Arrays.asList(annotationReader));

	}

	@Test
	public void get() throws Exception {

		expect(annotationReader.canRead(method)).andReturn(true);

		replayAll();

		assertThat(annotationReaderRegistryImpl.get(method),is( sameInstance(annotationReader)));

		verifyAll();

	}

	@Test
	public void get_noAnnotationReaderFound() throws Exception {

		expect(annotationReader.canRead(method)).andReturn(false);

		replayAll();

		assertThat(annotationReaderRegistryImpl.get(method),is(nullValue()));

		verifyAll();

	}

}