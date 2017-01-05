package com.github.osvaldopina.linkbuilder.hal.annotation.linkcreator;

import static org.junit.Assert.*;

import java.lang.reflect.Method;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

// TODO acertar para maps e outras coisas
@Ignore
public class EmbeddedPropertyDiscoverTest {

	private EmbeddedPropertyDiscover embeddedPropertyDiscover;

	private ObjectMapper mapper;

	private Method embeddedMethod = HalResourceWithEmbedded.class.getMethod("getEmbeddedProperty");

	public EmbeddedPropertyDiscoverTest() throws Exception {
	}


	@Before
	public void setUp() {
		embeddedPropertyDiscover = new EmbeddedPropertyDiscover();
		mapper = new ObjectMapper();

	}

	@Test
	public void getReaderMethodForHalResourceWithEmbedded() throws Exception {

//		assertEquals(embeddedMethod,
//				embeddedPropertyDiscover.getReaderMethodForHalEmbedded(mapper, HalResourceWithEmbedded.class));

	}

	@Test
	public void getReaderMethodForHalResourceWithoutEmbedded() throws Exception {

		assertNull(embeddedPropertyDiscover.getReaderMethodForHalEmbedded(mapper, HalResourceWithoutEmbedded.class));

	}


	public static class HalResourceWithEmbedded {


		@JsonProperty("_embedded")
		public Object getEmbeddedProperty() {
           return null;
		}

	}

	public static class HalResourceWithoutEmbedded {


		public Object getEmbeddedProperty() {
			return null;
		}

	}

}