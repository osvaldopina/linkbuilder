package com.github.osvaldopina.linkbuilder.hal.annotation.reader;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;

import com.github.osvaldopina.linkbuilder.hal.annotation.HalLinks;
import org.springframework.core.annotation.AnnotationUtils;

public class HalLinkAnnotationRetriever {

	public static final HalLinkAnnotationRetriever INSTANCE = new HalLinkAnnotationRetriever();

	HalLinkAnnotationRetriever() {

	}


	public Annotation getLinksAnnotation(AnnotatedElement  annotatedElement) {
		return 	getLinksAnnotation(annotatedElement.getAnnotations());
//		AnnotationUtils.findAnnotation(annotatedElement, HalLinks.class)
	}

	public Annotation getLinksAnnotation(Annotation[] declaredAnnotations) {
		for (Annotation annotation : declaredAnnotations) {
			if (HalLinks.class == annotation.annotationType() ||
					annotation.annotationType().getAnnotation(HalLinks.class) != null) {
				return annotation;
			}
		}
		return null;
	}
}
