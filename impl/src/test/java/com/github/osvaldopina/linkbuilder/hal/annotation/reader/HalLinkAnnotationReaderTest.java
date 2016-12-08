package com.github.osvaldopina.linkbuilder.hal.annotation.reader;

import static org.junit.Assert.*;

import com.github.osvaldopina.linkbuilder.annotation.reader.core.DestinationExtractor;
import com.github.osvaldopina.linkbuilder.annotation.reader.core.LinkRelExtractor;
import com.github.osvaldopina.linkbuilder.urigeneration.base.BaseUriDiscover;
import com.github.osvaldopina.linkbuilder.utils.ReflectionUtils;
import org.easymock.EasyMockRule;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Rule;

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


	@TestSubject
	HalLinkAnnotationReader halLinkAnnotationReader = new HalLinkAnnotationReader();





}