package com.github.osvaldopina.linkbuilder.hal.annotation.linkcreator.embedded.propertyreader;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.osvaldopina.linkbuilder.hal.annotation.linkcreator.embedded.propertyreader.EmbeddedMapPropertyReaderDiscover;
import org.junit.Before;
import org.junit.Test;

public class EmbeddedMapPropertyReaderDiscoverTest {

	private EmbeddedMapPropertyReaderDiscover embeddedMapPropertyReaderDiscover;

	private ObjectMapper mapper;

	private Method embeddedMethod = HalResourceWithEmbeddedMap.class.getMethod("getEmbeddedProperty");

	public EmbeddedMapPropertyReaderDiscoverTest() throws Exception {
	}


	@Before
	public void setUp() {
		embeddedMapPropertyReaderDiscover = new EmbeddedMapPropertyReaderDiscover();
		mapper = new ObjectMapper();

	}

	@Test
	public void getReaderMethodForHalResourceWithEmbedded() throws Exception {


        List<MethodChain> readerMethodChains = embeddedMapPropertyReaderDiscover.
                getReaderMethodChains(mapper, HalResourceWithEmbeddedMap.class);

        assertThat(readerMethodChains, is(notNullValue()));
        assertThat(readerMethodChains, hasSize(1));
        assertThat(readerMethodChains.get(0).getMethods(), contains(embeddedMethod));

	}

	@Test
	public void getReaderMethodForHalResourceWithoutEmbedded() throws Exception {

        List<MethodChain> readMethods = embeddedMapPropertyReaderDiscover.
                getReaderMethodChains(mapper, HalResourceWithoutEmbedded.class);

        assertThat(readMethods, is(notNullValue()));
		assertThat(readMethods, hasSize(0));

	}


	public static class HalResourceWithEmbeddedMap {


		@JsonProperty("_embedded")
		public Map<String,Object> getEmbeddedProperty() {
           return null;
		}

	}

	public static class HalResourceWithoutEmbedded {


		public Object getEmbeddedProperty() {
			return null;
		}

	}

}