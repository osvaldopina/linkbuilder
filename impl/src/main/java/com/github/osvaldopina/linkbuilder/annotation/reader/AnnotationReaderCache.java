package com.github.osvaldopina.linkbuilder.annotation.reader;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import com.github.osvaldopina.linkbuilder.annotation.reader.properties.LinkAnnotationProperties;

public class AnnotationReaderCache implements AnnotationReader{

	ConcurrentHashMap<Method,List<LinkAnnotationProperties>> methodAnnotations =
			new ConcurrentHashMap<Method, List<LinkAnnotationProperties>>();

	ConcurrentHashMap<Method,Boolean> canReadMethod =
			new ConcurrentHashMap<Method, Boolean>();

	ConcurrentHashMap<Class<?>,List<LinkAnnotationProperties>> resourceTypeAnnotations =
			new ConcurrentHashMap<Class<?>, List<LinkAnnotationProperties>>();

	ConcurrentHashMap<Class<?>,Boolean> canReadResourceType =
			new ConcurrentHashMap<Class<?>, Boolean>();

	private AnnotationReader annotationReader;

	public AnnotationReaderCache(AnnotationReader annotationReader) {
		this.annotationReader = annotationReader;
	}

	@Override
	public boolean canRead(Method method) {
		Boolean canRead = canReadMethod.get(method);

		if (canRead == null) {
			canRead = annotationReader.canRead(method);
			canReadMethod.put(method, canRead);
		}
		return canRead;
	}

	@Override
	public boolean canRead(Class<?> resourceType) {
		Boolean canRead = canReadResourceType.get(resourceType);

		if (canRead == null) {
			canRead = annotationReader.canRead(resourceType);
			canReadResourceType.put(resourceType, canRead);
		}
		return canRead;
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
	public List<LinkAnnotationProperties> read(Class<?> resourceType) {

		List<LinkAnnotationProperties> linkAnnotationProperties = resourceTypeAnnotations.get(resourceType);

		if (linkAnnotationProperties == null) {
			linkAnnotationProperties = annotationReader.read(resourceType);
			resourceTypeAnnotations.put(resourceType, linkAnnotationProperties);
		}

		return linkAnnotationProperties;
	}
}
