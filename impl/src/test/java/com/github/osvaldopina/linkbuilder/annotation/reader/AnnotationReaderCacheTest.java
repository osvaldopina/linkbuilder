package com.github.osvaldopina.linkbuilder.annotation.reader;

import com.github.osvaldopina.linkbuilder.annotation.reader.properties.LinkAnnotationProperties;
import org.easymock.EasyMockRule;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static org.easymock.EasyMock.expect;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.sameInstance;
import static org.hamcrest.core.Is.is;


public class AnnotationReaderCacheTest extends EasyMockSupport {

    @Rule
    public EasyMockRule mocks = new EasyMockRule(this);

    @Mock
    private AnnotationReader annotationReader;

    private AnnotationReaderCache annotationReaderCache;

    Method method = Object.class.getMethod("toString");

    Class<?> resourceType = AnnotationReaderCacheTest.class;

    List<LinkAnnotationProperties> annotationProperties = new ArrayList<LinkAnnotationProperties>();


    public AnnotationReaderCacheTest() throws Exception {
    }

    @Before
    public void setUp() {
        annotationReaderCache = new AnnotationReaderCache(annotationReader);
    }

    @Test
    public void canRead_methodNotInCacheCanReadTrue() {
        expect(annotationReader.canRead(method)).andReturn(true);

        replayAll();

        assertThat(annotationReaderCache.canRead(method), is(true));

        verifyAll();
    }

    @Test
    public void canRead_methodNotInCacheCanReadFalse() {
        expect(annotationReader.canRead(method)).andReturn(false);

        replayAll();

        assertThat(annotationReaderCache.canRead(method), is(false));

        verifyAll();
    }

    @Test
    public void canRead_methodAlreadyInCacheCanReadTrue() {
        expect(annotationReader.canRead(method)).andReturn(true);

        replayAll();

        annotationReaderCache.canRead(method);
        assertThat(annotationReaderCache.canRead(method), is(true));

        verifyAll();
    }

    @Test
    public void canRead_methodAlreadyInCacheCanReadFalse() {
        expect(annotationReader.canRead(method)).andReturn(false);

        replayAll();

        annotationReaderCache.canRead(method);
        assertThat(annotationReaderCache.canRead(method), is(false));

        verifyAll();
    }

    @Test
    public void canRead_resourceTypeNotInCacheCanReadTrue() {
        expect(annotationReader.canRead(resourceType)).andReturn(true);

        replayAll();

        assertThat(annotationReaderCache.canRead(resourceType), is(true));

        verifyAll();
    }

    @Test
    public void canRead_resourceTypeNotInCacheCanReadFalse() {
        expect(annotationReader.canRead(resourceType)).andReturn(false);

        replayAll();

        assertThat(annotationReaderCache.canRead(resourceType), is(false));

        verifyAll();
    }

    @Test
    public void canRead_resourceTypeAlreadyInCacheCanReadTrue() {
        expect(annotationReader.canRead(resourceType)).andReturn(true);

        replayAll();

        annotationReaderCache.canRead(resourceType);
        assertThat(annotationReaderCache.canRead(resourceType), is(true));

        verifyAll();
    }

    @Test
    public void canRead_resourceTypeAlreadyInCacheCanReadFalse() {
        expect(annotationReader.canRead(resourceType)).andReturn(false);

        replayAll();

        annotationReaderCache.canRead(resourceType);
        assertThat(annotationReaderCache.canRead(resourceType), is(false));

        verifyAll();
    }


    @Test
    public void read_methodNotInCache() {
        expect(annotationReader.read(method)).andReturn(annotationProperties);

        replayAll();

        assertThat(annotationReaderCache.read(method), is(sameInstance(annotationProperties)));

        verifyAll();
    }

    @Test
    public void read_methodInCache() {
        expect(annotationReader.read(method)).andReturn(annotationProperties);

        replayAll();

        annotationReaderCache.read(method);
        assertThat(annotationReaderCache.read(method), is(sameInstance(annotationProperties)));

        verifyAll();
    }

    @Test
    public void read_resourceTypeNotInCache() {
        expect(annotationReader.read(resourceType)).andReturn(annotationProperties);

        replayAll();

        annotationReaderCache.read(resourceType);
        assertThat(annotationReaderCache.read(resourceType), is(sameInstance(annotationProperties)));

        verifyAll();
    }

    @Test
    public void read_resourceTypeAlreadyInCache() {
        expect(annotationReader.read(resourceType)).andReturn(annotationProperties);

        replayAll();

        annotationReaderCache.read(resourceType);
        assertThat(annotationReaderCache.read(resourceType), is(sameInstance(annotationProperties)));

        verifyAll();
    }


}