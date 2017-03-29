package com.github.osvaldopina.linkbuilder.hal.annotation.linkcreator.embedded.valuesdiscover.flatnizer;

import static org.easymock.EasyMock.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.easymock.EasyMockRule;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class FlatnizerRegistryTest extends EasyMockSupport {

    @Rule
    public EasyMockRule mocks = new EasyMockRule(this);

    @Mock
    private EmptyFlatnizer emptyFlatnizer;

    @Mock
    private Flatnizer anyFlatnizer;

    private Object target = new Object();

    private FlatnizerRegistry flatnizerRegistry;



    @Before
    public void setUp(){
        flatnizerRegistry = new FlatnizerRegistry(Arrays.asList(anyFlatnizer), emptyFlatnizer);
    }

    @Test
    public void getAnyFlatnizer() throws Exception {
        Set<Object> values = new HashSet<Object>();

        expect(anyFlatnizer.canFlat(target)).andReturn(true);
        expectLastCall();

        replayAll();

        assertThat(flatnizerRegistry.get(target), is(anyFlatnizer));

        verifyAll();

    }

    @Test
    public void getEmptyFlatnizer() throws Exception {

        expect(anyFlatnizer.canFlat(target)).andReturn(false);
        expectLastCall();

        replayAll();

        assertThat(flatnizerRegistry.get(target), is((Flatnizer) emptyFlatnizer));

        verifyAll();

    }

}