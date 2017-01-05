package com.github.osvaldopina.linkbuilder.hal.annotation.linkcreator;

import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.osvaldopina.linkbuilder.LinkBuilderException;

public class EmbeddePropertyCache {

	private static final Method HAL_RESOURCE_NO_EMBEDDED_PROPERTY;

	static {
		try {
			HAL_RESOURCE_NO_EMBEDDED_PROPERTY = EmbeddePropertyCache.class.getMethod("toString");
		} catch (NoSuchMethodException e) {
			throw new LinkBuilderException("Internal error! ");
		}
	}

	private EmbeddedPropertyDiscover embeddedPropertyDiscover;
	private ConcurrentHashMap<Class<?>,Method> cache = new ConcurrentHashMap<Class<?>, Method>();


	public EmbeddePropertyCache(EmbeddedPropertyDiscover embeddedPropertyDiscover) {
		this.embeddedPropertyDiscover = embeddedPropertyDiscover;
	}

	public Method getReaderMethodForHalEmbedded(ObjectMapper mapper, Class<?> halResourceType) {
		Method readerMethod = cache.get(halResourceType);

		if (readerMethod == null) {
			readerMethod = embeddedPropertyDiscover.getReaderMethodForHalEmbedded(mapper, halResourceType);
			if (readerMethod == null) {
				cache.put(halResourceType, HAL_RESOURCE_NO_EMBEDDED_PROPERTY);
			} else {
				cache.put(halResourceType, readerMethod);
			}
		} else 	if (readerMethod.equals(HAL_RESOURCE_NO_EMBEDDED_PROPERTY)) {
			return null;
		}
		return readerMethod;
	}



}
