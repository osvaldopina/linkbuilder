package com.github.osvaldopina.linkbuilder.hal.annotation.linkcreator;

import java.lang.reflect.Method;
import java.util.Map;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition;

public class EmbeddedPropertyDiscover {

	public static final EmbeddedPropertyDiscover INSTANCE = new EmbeddedPropertyDiscover();


    EmbeddedPropertyDiscover() {

	}

	public Method getReaderMethodForHalEmbedded(ObjectMapper mapper, Class<?> halResourceType) {

		JavaType target = mapper.constructType(halResourceType);
		BeanDescription beanDescription = mapper.getDeserializationConfig().introspect(target);

		for(BeanPropertyDefinition beanPropertyDefinition: beanDescription.findProperties()) {
			if ("_embedded".equals(beanPropertyDefinition.getName())) {
				if (Map.class.isAssignableFrom(beanPropertyDefinition.getGetter().getMember().getReturnType())) {
					return beanPropertyDefinition.getGetter().getMember();
				}
				else {
					JavaType embeddedSubType = mapper.constructType(beanPropertyDefinition.getGetter().getMember().getReturnType());
					BeanDescription embeddedSubTypeDescription = mapper.getDeserializationConfig().introspect(embeddedSubType);
					if (embeddedSubTypeDescription.findProperties().size() == 1) {
						return embeddedSubTypeDescription.findProperties().get(0).getGetter().getMember();
					}
				}
				return beanPropertyDefinition.getGetter().getMember();
			}
		}
		return null;
	}

}
