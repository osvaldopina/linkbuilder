package com.github.osvaldopina.linkbuilder.annotation.reader.core;

import java.lang.annotation.Annotation;

import com.github.osvaldopina.linkbuilder.utils.ReflectionUtils;
import com.github.osvaldopina.linkbuilder.utils.Utils;

class PropertyAsStringExtractor {

		public static PropertyAsStringExtractor INSTANCE = new PropertyAsStringExtractor();

		private ReflectionUtils reflectionUtils = ReflectionUtils.INSTANCE;
		private Utils utils = Utils.INSTANCE;

	    PropertyAsStringExtractor() {

		}

		public String extract(Annotation annotation, String propName) {
			if (reflectionUtils.hasMethod(annotation, propName)) {
				Object value = reflectionUtils.callMethod(Object.class, annotation, propName);

				if (utils.isPresent(value)) {
					return value.toString();
				}
				else {
					return null;
				}
			}
			else {
				return null;
			}
		}

}
