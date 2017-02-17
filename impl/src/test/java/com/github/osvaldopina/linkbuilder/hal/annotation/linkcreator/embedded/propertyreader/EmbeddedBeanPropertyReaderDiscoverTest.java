package com.github.osvaldopina.linkbuilder.hal.annotation.linkcreator.embedded.propertyreader;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;


public class EmbeddedBeanPropertyReaderDiscoverTest {

	private EmbeddedBeanPropertyReaderDiscover embeddedBeanPropertyReaderDiscover;

	private ObjectMapper mapper;

	private Method embeddedMethod = HalResourceWithEmbeddedBean.class.getMethod("getEmbeddedProperty");

    private Method embeddedFirstEmbeddedMethod = EmbeddedBean.class.getMethod("getFirstEmbedded");

    private Method embeddedSecondEmbeddedMethod = EmbeddedBean.class.getMethod("getSecondEmbedded");

	public EmbeddedBeanPropertyReaderDiscoverTest() throws Exception {
	}


	@Before
	public void setUp() {
		embeddedBeanPropertyReaderDiscover = new EmbeddedBeanPropertyReaderDiscover();
		mapper = new ObjectMapper();

	}

	@Test
	public void getReaderMethodForHalResourceWithEmbedded() throws Exception {

        List<MethodChain> readerMethodChains = embeddedBeanPropertyReaderDiscover.
                getReaderMethodChains(mapper, HalResourceWithEmbeddedBean.class);

        assertThat(readerMethodChains, is(notNullValue()));
        assertThat(readerMethodChains, hasSize(2));

		assertThat(readerMethodChains, containsInAnyOrder(
				new MethodChain(Arrays.asList(embeddedMethod, embeddedFirstEmbeddedMethod)),
				new MethodChain(Arrays.asList(embeddedMethod, embeddedSecondEmbeddedMethod))));

	}

	@Test
	public void getReaderMethodForHalResourceWithoutEmbedded() throws Exception {

        List<MethodChain> readerMethodChains = embeddedBeanPropertyReaderDiscover.
                getReaderMethodChains(mapper, HalResourceWithoutEmbedded.class);

        assertThat(readerMethodChains, is(notNullValue()));
        assertThat(readerMethodChains, hasSize(0));

	}


	public static class HalResourceWithEmbeddedBean {


		@JsonProperty("_embedded")
		public EmbeddedBean getEmbeddedProperty() {
           return null;
		}

	}

	public static class EmbeddedBean {

		public Object getFirstEmbedded() {
			return null;
		}

		public Object getSecondEmbedded() {
			return null;
		}
	}

	public static class HalResourceWithoutEmbedded {


		public Object getEmbeddedProperty() {
			return null;
		}

	}

}