package com.github.osvaldopina.linkbuilder.hal.annotation.linkcreator.embedded.valuesdiscover.flatnizer;

import com.github.osvaldopina.linkbuilder.hal.annotation.linkcreator.embedded.propertyreader.EmbeddedBeanPropertyReaderDiscover;
import org.easymock.EasyMockRule;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.*;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.core.Is.is;


public class CollectionFlatnizerTest extends EasyMockSupport {

    @Rule
    public EasyMockRule mocks = new EasyMockRule(this);

    @Mock
    private FlatnizerRegistry flatnizerRegistry;

    @Mock
    private Flatnizer emptyFlatnizer;

    private List<Object> list;

    private Object nonList = new Object();

    private CollectionFlatnizer collectionFlatnizer;

    @Before
    public void setUp() {
        list = new ArrayList<Object>();
        list.add("value-1");
        list.add("value-2");

        collectionFlatnizer = new CollectionFlatnizer(flatnizerRegistry);
    }


    @Test
    public void canFlat() throws Exception {
       assertThat(collectionFlatnizer.canFlat(list), is(true));
    }

    @Test
    public void canFlat_nonList() throws Exception {
        assertThat(collectionFlatnizer.canFlat(nonList), is(false));
    }

    @Test
    public void flatAndAddToSet() throws Exception {
        Set<Object> values = new HashSet<Object>();

        expect(flatnizerRegistry.get("value-1")).andReturn(emptyFlatnizer);
        emptyFlatnizer.flatAndAddToSet("value-1",values);
        expectLastCall();

        expect(flatnizerRegistry.get("value-2")).andReturn(emptyFlatnizer);
        emptyFlatnizer.flatAndAddToSet("value-2",values);
        expectLastCall();

        replayAll();

        collectionFlatnizer.flatAndAddToSet(list,values);

        verifyAll();

    }
}