package com.github.osvaldopina.linkbuilder.hal.annotation.linkcreator.embedded.propertyreader;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition;

public class EmbeddedMapPropertyReaderDiscover implements EmbeddedProperyReaderDiscover {

	public static final EmbeddedMapPropertyReaderDiscover INSTANCE = new EmbeddedMapPropertyReaderDiscover();


    EmbeddedMapPropertyReaderDiscover() {

	}

	public List<MethodChain> getReaderMethodChains(ObjectMapper mapper, Class<?> halResourceType) {

		JavaType target = mapper.constructType(halResourceType);
		BeanDescription beanDescription = mapper.getDeserializationConfig().introspect(target);

		for(BeanPropertyDefinition beanPropertyDefinition: beanDescription.findProperties()) {
			if ("_embedded".equals(beanPropertyDefinition.getName())) {
				if (Map.class.isAssignableFrom(beanPropertyDefinition.getGetter().getMember().getReturnType())) {
					return Arrays.asList(new MethodChain(Arrays.asList(beanPropertyDefinition.getGetter().getMember())));
				}
			}
		}
		return Collections.emptyList();
	}

}
