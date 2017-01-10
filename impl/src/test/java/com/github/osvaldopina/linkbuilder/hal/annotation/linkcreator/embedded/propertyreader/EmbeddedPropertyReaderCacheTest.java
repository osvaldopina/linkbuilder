package com.github.osvaldopina.linkbuilder.hal.annotation.linkcreator.embedded.propertyreader;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.easymock.EasyMockRule;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.easymock.EasyMock.expect;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class EmbeddedPropertyReaderCacheTest extends EasyMockSupport {

    @Rule
    public EasyMockRule mocks = new EasyMockRule(this);

    @Mock
    EmbeddedProperyReaderDiscover embeddedProperyReaderDiscover;

    @Mock
    MethodChain methodChain;

    List<MethodChain> methodChains;

    @Mock
    ObjectMapper objectMapper;

    Class<?> type = EmbeddedPropertyReaderCacheTest.class;

    EmbeddedPropertyReaderCache embeddedPropertyReaderCache;

    @Before
    public void setUp() {
        embeddedPropertyReaderCache = new EmbeddedPropertyReaderCache(embeddedProperyReaderDiscover);
        methodChains = new ArrayList<MethodChain>();
        methodChains.add(methodChain);
    }

    @Test
    public void getReaderMethodForHalEmbedded_methodChainDiscoveredIsNull() throws Exception {
        expect(embeddedProperyReaderDiscover.getReaderMethodChains(objectMapper, type)).andReturn(null);

        replayAll();

        assertThat(embeddedPropertyReaderCache.getReaderMethodForHalEmbedded(objectMapper, type), hasSize(0));
        assertThat(embeddedPropertyReaderCache.getCache().size(), is(1));
        assertThat(embeddedPropertyReaderCache.getCache().keySet(), hasItem(type));
        assertThat(embeddedPropertyReaderCache.getCache().values(),
                hasItem(EmbeddedPropertyReaderCache.HAL_RESOURCE_NO_EMBEDDED_PROPERTY));

        verifyAll();


    }

    @Test
    public void getReaderMethodForHalEmbedded() throws Exception {
        expect(embeddedProperyReaderDiscover.getReaderMethodChains(objectMapper, type)).andReturn(methodChains);

        replayAll();

        assertThat(embeddedPropertyReaderCache.getReaderMethodForHalEmbedded(objectMapper, type), hasItem(methodChain));
        assertThat(embeddedPropertyReaderCache.getCache().size(), is(1));
        assertThat(embeddedPropertyReaderCache.getCache().keySet(), hasItem(type));
        assertThat(embeddedPropertyReaderCache.getCache().values(),
                hasItem(methodChains));

        verifyAll();


    }

    @Test
    public void getReaderMethodForHalEmbedded_secondCallGoesOnlyOnCache() throws Exception {
        expect(embeddedProperyReaderDiscover.getReaderMethodChains(objectMapper, type)).andReturn(methodChains);

        replayAll();

        // first call
        embeddedPropertyReaderCache.getReaderMethodForHalEmbedded(objectMapper, type);

        // second call
        assertThat(embeddedPropertyReaderCache.getReaderMethodForHalEmbedded(objectMapper, type), hasItem(methodChain));
        assertThat(embeddedPropertyReaderCache.getCache().size(), is(1));
        assertThat(embeddedPropertyReaderCache.getCache().keySet(), hasItem(type));
        assertThat(embeddedPropertyReaderCache.getCache().values(),
                hasItem(methodChains));

        verifyAll();


    }

}