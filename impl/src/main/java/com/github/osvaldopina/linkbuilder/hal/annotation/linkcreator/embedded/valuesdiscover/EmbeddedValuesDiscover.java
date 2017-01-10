package com.github.osvaldopina.linkbuilder.hal.annotation.linkcreator.embedded.valuesdiscover;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.osvaldopina.linkbuilder.hal.annotation.linkcreator.embedded.propertyreader.EmbeddedPropertyReaderCache;
import com.github.osvaldopina.linkbuilder.hal.annotation.linkcreator.embedded.propertyreader.EmbeddedPropertyReaderDiscovers;
import com.github.osvaldopina.linkbuilder.hal.annotation.linkcreator.embedded.propertyreader.MethodChain;
import com.github.osvaldopina.linkbuilder.hal.annotation.linkcreator.embedded.valuesdiscover.flatnizer.ObjectFlatinizer;
import org.springframework.hateoas.ResourceSupport;

import java.util.*;

public class EmbeddedValuesDiscover {

    public static final EmbeddedValuesDiscover INSTANCE = new EmbeddedValuesDiscover();

    private ObjectFlatinizer objectFlatinizer = ObjectFlatinizer.INSTANCE;

    private EmbeddedPropertyReaderCache embeddedPropertyReaderCache =
            new EmbeddedPropertyReaderCache(EmbeddedPropertyReaderDiscovers.INSTANCE);


    EmbeddedValuesDiscover() {
    }

    public Set<Object> getEmbeddedValues(ObjectMapper objectMapper, Object resource) {
        Set<Object> embeddedValues = new HashSet<Object>();
        getEmbeddedValuesAndAdToSet(objectMapper, resource, embeddedValues);
        return embeddedValues;
    }

    private void getEmbeddedValuesAndAdToSet(ObjectMapper objectMapper, Object resource, Set<Object> embeddedResources) {
        List<MethodChain> methodChains = embeddedPropertyReaderCache.
                getReaderMethodForHalEmbedded(objectMapper, resource.getClass());

        Object embeddedValue;
        for(MethodChain methodChain: methodChains) {
            embeddedValue = methodChain.execCalls(resource);

            if (embeddedValue != null) {
                embeddedResources.addAll(objectFlatinizer.flatnize(embeddedValue));
            }
        }
    }
}
