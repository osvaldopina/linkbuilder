package com.github.osvaldopina.linkbuilder.hal.annotation.reader;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import com.github.osvaldopina.linkbuilder.hal.annotation.HalLinks;

public class HalLinkAnnotationRetriever {

	public static final HalLinkAnnotationRetriever INSTANCE = new HalLinkAnnotationRetriever();

	HalLinkAnnotationRetriever() {

	}

	public Annotation getLinksAnnotation(Annotation[] declaredAnnotations) {
		for(Annotation annotation: declaredAnnotations) {
			if (HalLinks.class == annotation.annotationType() ||
					annotation.annotationType().getAnnotation(HalLinks.class) != null)  {
				return annotation;
			}
		}
		return null;

	}


}
