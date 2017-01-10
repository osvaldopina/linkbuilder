package com.github.osvaldopina.linkbuilder.hal.annotation.linkcreator.embedded.propertyreader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.fasterxml.jackson.databind.ObjectMapper;

public class EmbeddedPropertyReaderCache {

    protected static final List<MethodChain> HAL_RESOURCE_NO_EMBEDDED_PROPERTY = new ArrayList<MethodChain>();


    private EmbeddedProperyReaderDiscover embeddedProperyReaderDiscover;
    private ConcurrentHashMap<Class<?>, List<MethodChain>> cache = new ConcurrentHashMap<Class<?>, List<MethodChain>>();


    public EmbeddedPropertyReaderCache(EmbeddedProperyReaderDiscover embeddedProperyReaderDiscover) {
        this.embeddedProperyReaderDiscover = embeddedProperyReaderDiscover;
    }

    public List<MethodChain> getReaderMethodForHalEmbedded(ObjectMapper mapper, Class<?> halResourceType) {
        List<MethodChain> readerMethodChain = cache.get(halResourceType);

        if (readerMethodChain == null) {
            readerMethodChain = embeddedProperyReaderDiscover.getReaderMethodChains(mapper, halResourceType);
            if (readerMethodChain == null) {
                readerMethodChain = HAL_RESOURCE_NO_EMBEDDED_PROPERTY;
            }
            cache.put(halResourceType, readerMethodChain);
        }
        return readerMethodChain;
    }

    protected Map<Class<?>, List<MethodChain>> getCache() {
        return Collections.unmodifiableMap(cache);
    }


}
