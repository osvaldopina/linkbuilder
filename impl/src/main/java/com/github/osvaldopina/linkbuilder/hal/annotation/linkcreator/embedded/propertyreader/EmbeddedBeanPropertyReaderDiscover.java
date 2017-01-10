package com.github.osvaldopina.linkbuilder.hal.annotation.linkcreator.embedded.propertyreader;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition;

import java.lang.reflect.Method;
import java.util.*;

public class EmbeddedBeanPropertyReaderDiscover implements EmbeddedProperyReaderDiscover {

	public static final EmbeddedBeanPropertyReaderDiscover INSTANCE = new EmbeddedBeanPropertyReaderDiscover();

    EmbeddedBeanPropertyReaderDiscover() {

	}

	public List<MethodChain> getReaderMethodChains(ObjectMapper mapper, Class<?> halResourceType) {

		JavaType target = mapper.constructType(halResourceType);
		BeanDescription beanDescription = mapper.getDeserializationConfig().introspect(target);
        List<MethodChain> readerMethods = new ArrayList<MethodChain>();
		for(BeanPropertyDefinition beanPropertyDefinition: beanDescription.findProperties()) {
			if ("_embedded".equals(beanPropertyDefinition.getName())) {
				if (! Map.class.isAssignableFrom(beanPropertyDefinition.getGetter().getMember().getReturnType())) {
					Method embeddedProperty = beanPropertyDefinition.getGetter().getMember();
					JavaType embeddedSubType = mapper.constructType(beanPropertyDefinition.getGetter().getMember().getReturnType());
					BeanDescription embeddedSubTypeDescription = mapper.getDeserializationConfig().introspect(embeddedSubType);
					for(BeanPropertyDefinition embeddedSubTypePropertyDefinition: embeddedSubTypeDescription.findProperties()) {
						readerMethods.add(
								new MethodChain(Arrays.asList(embeddedProperty, embeddedSubTypePropertyDefinition.getGetter().getMember())));
					}
				}
				return readerMethods;
			}
		}
		return Collections.emptyList();
	}

}
