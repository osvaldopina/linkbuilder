package com.github.osvaldopina.linkbuilder.hal.annotation.linkcreator.embedded.valuesdiscover.flatnizer;

import static org.easymock.EasyMock.*;
import static org.hamcrest.core.Is.*;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.easymock.EasyMockRule;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Rule;
import org.junit.Test;

public class ObjectFlatinizerTest  extends EasyMockSupport {

    @Rule
    public EasyMockRule mocks = new EasyMockRule(this);

    @Mock
    private FlatnizerRegistry flatnizerRegistry;

    @Mock
    private Flatnizer anyFlatnizer;

    @Mock
    private ListFactory listFactory;

    private List<Object> set = new ArrayList<Object>();

    private Object target = new Object();

    @TestSubject
    private ObjectFlatinizer objectFlatinizer = new ObjectFlatinizer();

    @Test
    public void flatnize() throws Exception {
        expect(listFactory.create()).andReturn(set);
        expect(flatnizerRegistry.get(target)).andReturn(anyFlatnizer);
        anyFlatnizer.flatAndAddToSet(target, set);

        replayAll();

        assertThat(objectFlatinizer.flatnize(target), is(set));

        verifyAll();


    }
}