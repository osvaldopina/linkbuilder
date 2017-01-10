package com.github.osvaldopina.linkbuilder.hal.annotation.linkcreator.embedded.propertyreader;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EmbeddedPropertyReaderDiscovers implements EmbeddedProperyReaderDiscover {

    public static final EmbeddedPropertyReaderDiscovers INSTANCE = new EmbeddedPropertyReaderDiscovers();

    private EmbeddedBeanPropertyReaderDiscover embeddedBeanPropertyReaderDiscover =
            EmbeddedBeanPropertyReaderDiscover.INSTANCE;

    private EmbeddedMapPropertyReaderDiscover embeddedMapPropertyReaderDiscover =
            EmbeddedMapPropertyReaderDiscover.INSTANCE;


    EmbeddedPropertyReaderDiscovers() {

    }

    @Override
    public List<MethodChain> getReaderMethodChains(ObjectMapper mapper, Class<?> halResourceType) {
        List<MethodChain> methodChains = new ArrayList<MethodChain>();
        methodChains.addAll(embeddedBeanPropertyReaderDiscover.getReaderMethodChains(mapper, halResourceType));
        methodChains.addAll(embeddedMapPropertyReaderDiscover.getReaderMethodChains(mapper, halResourceType));

        return Collections.unmodifiableList(methodChains);
    }
}
