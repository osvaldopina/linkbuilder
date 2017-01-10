package com.github.osvaldopina.linkbuilder.hal.annotation.linkcreator.embedded.propertyreader;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

public interface EmbeddedProperyReaderDiscover {

    List<MethodChain> getReaderMethodChains(ObjectMapper mapper, Class<?> halResourceType);

}
