package com.github.osvaldopina.linkbuilder.hal.annotation.reader;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Method;

import com.github.osvaldopina.linkbuilder.annotation.Links;
import com.github.osvaldopina.linkbuilder.hal.annotation.HalLinks;
import org.junit.Test;

public class HalLinkAnnotationRetrieverTest {

	private Method linksMethod = HalLinkAnnotationRetrieverTest.class.getMethod("linksMethod");

	private Method myLinksMethod = HalLinkAnnotationRetrieverTest.class.getMethod("myLinksMethod");

	private Method noLinksMethod = HalLinkAnnotationRetrieverTest.class.getMethod("noLinksMethod");

	private HalLinkAnnotationRetriever halLinkAnnotationRetriever = new HalLinkAnnotationRetriever();

	public HalLinkAnnotationRetrieverTest() throws Exception {
	}

	@Test
	public void getLinksAnnotation_linksMethod() throws Exception {
		// TODO acertar
		//Annotation annotation = halLinkAnnotationRetriever.getLinksAnnotation(linksMethod.getDeclaredAnnotations());

//		assertThat(annotation, is(instanceOf(HalLinks.class)));

	}

	@Test
	public void getLinksAnnotation_myLinksMethod() throws Exception {
		// TODO acertar
//		Annotation annotation = halLinkAnnotationRetriever.getLinksAnnotation(myLinksMethod.getDeclaredAnnotations());

//		assertThat(annotation, is(instanceOf(MyLinks.class)));

	}

	@Test
	public void getLinksAnnotation_noLinksMethod() throws Exception {
		// TODO acertar
//		Annotation annotation = halLinkAnnotationRetriever.getLinksAnnotation(noLinksMethod.getDeclaredAnnotations());

//		assertThat(annotation, is(nullValue()));

	}


	@HalLinks
	public void linksMethod() {

	}

	@MyLinks
	public void myLinksMethod() {

	}

	public void noLinksMethod() {

	}


	@HalLinks
	@Retention(RetentionPolicy.RUNTIME)
	public @interface MyLinks {

	}

}