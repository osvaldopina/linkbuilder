package com.github.osvaldopina.linkbuilder.hal.annotation.linkcreator.embedded.propertyreader;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.easymock.EasyMockRule;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Rule;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

import static org.easymock.EasyMock.expect;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.*;

public class EmbeddedPropertyReaderDiscoversTest extends EasyMockSupport {

    @Rule
    public EasyMockRule mocks = new EasyMockRule(this);

    @Mock
    private EmbeddedBeanPropertyReaderDiscover embeddedBeanPropertyReaderDiscover;

    @Mock
    private EmbeddedMapPropertyReaderDiscover embeddedMapPropertyReaderDiscover;

    @Mock
    private ObjectMapper objectMapper;

    private Class<?> type = EmbeddedPropertyReaderDiscoversTest.class;

    @TestSubject
    private EmbeddedPropertyReaderDiscovers embeddedPropertyReaderDiscovers = new EmbeddedPropertyReaderDiscovers();

    @Test
    public void testGetReaderMethodChains() throws Exception {
        expect(
                embeddedBeanPropertyReaderDiscover.getReaderMethodChains(objectMapper, type)).
                andReturn(Collections.<MethodChain>emptyList());

        expect(
                embeddedMapPropertyReaderDiscover.getReaderMethodChains(objectMapper, type)).
                andReturn(Collections.<MethodChain>emptyList());

        replayAll();

       List<MethodChain> methodChainList = embeddedPropertyReaderDiscovers.getReaderMethodChains(objectMapper, type);

        verifyAll();

        assertThat(methodChainList, hasSize(0));


    }
}