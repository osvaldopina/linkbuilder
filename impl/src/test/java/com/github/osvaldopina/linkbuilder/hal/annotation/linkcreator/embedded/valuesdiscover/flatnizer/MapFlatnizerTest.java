package com.github.osvaldopina.linkbuilder.hal.annotation.linkcreator.embedded.valuesdiscover.flatnizer;

import org.easymock.EasyMockRule;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.*;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.core.Is.is;


public class MapFlatnizerTest  extends EasyMockSupport {

    @Rule
    public EasyMockRule mocks = new EasyMockRule(this);

    @Mock
    private FlatnizerRegistry flatnizerRegistry;

    @Mock
    private Flatnizer emptyFlatnizer;


    private Map<Object,Object> map = new HashMap<Object,Object>();

    private Object nonMap = new Object();

    private MapFlatnizer mapFlatnizer;

    @Before
    public void setUp() {
        map = new HashMap<Object, Object>();
        map.put("key-1", "value-1");
        map.put("key-2", "value-2");

        mapFlatnizer = new MapFlatnizer(flatnizerRegistry);
    }


    @Test
    public void canFlat() throws Exception {
       assertThat(mapFlatnizer.canFlat(map), is(true));
    }

    @Test
    public void canFlat_nonMap() throws Exception {
        assertThat(mapFlatnizer.canFlat(nonMap), is(false));
    }

    @Test
    public void flatAndAddToSet() throws Exception {

        List<Object> values = new ArrayList<Object>();

        expect(flatnizerRegistry.get("value-1")).andReturn(emptyFlatnizer);
        emptyFlatnizer.flatAndAddToSet("value-1",values);
        expectLastCall();

        expect(flatnizerRegistry.get("value-2")).andReturn(emptyFlatnizer);
        emptyFlatnizer.flatAndAddToSet("value-2",values);
        expectLastCall();

        replayAll();

        mapFlatnizer.flatAndAddToSet(map,values);

        verifyAll();
    }
}