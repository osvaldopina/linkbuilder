package com.github.osvaldopina.linkbuilder.annotation.reader;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;

import com.github.osvaldopina.linkbuilder.annotation.reader.properties.LinkAnnotationProperties;

public class AnnotationReaderCache implements AnnotationReader{

	HashMap<Method,List<LinkAnnotationProperties>> methodAnnotations =
			new HashMap<Method, List<LinkAnnotationProperties>>();

	HashMap<Class<?>,List<LinkAnnotationProperties>> resourceTypeAnnotations =
			new HashMap<Class<?>, List<LinkAnnotationProperties>>();

	private AnnotationReader annotationReader;

	public AnnotationReaderCache(AnnotationReader annotationReader) {
		this.annotationReader = annotationReader;
	}

	@Override
	public boolean canRead(Method method) {
		return methodAnnotations.get(method) != null || annotationReader.canRead(method);
	}

	@Override
	public boolean canRead(Class<?> payloadType) {
		return resourceTypeAnnotations.get(payloadType) != null || annotationReader.canRead(payloadType);
	}

	@Override
	public List<LinkAnnotationProperties> read(Method method) {

		List<LinkAnnotationProperties> linkAnnotationProperties = methodAnnotations.get(method);

		if (linkAnnotationProperties == null) {
			linkAnnotationProperties = annotationReader.read(method);
			methodAnnotations.put(method, linkAnnotationProperties);
		}

		return linkAnnotationProperties;
	}

	@Override
	public List<LinkAnnotationProperties> read(Class<?> payloadType) {

		List<LinkAnnotationProperties> linkAnnotationProperties = resourceTypeAnnotations.get(payloadType);

		if (linkAnnotationProperties == null) {
			linkAnnotationProperties = annotationReader.read(payloadType);
			resourceTypeAnnotations.put(payloadType, linkAnnotationProperties);
		}

		return linkAnnotationProperties;
	}
}
