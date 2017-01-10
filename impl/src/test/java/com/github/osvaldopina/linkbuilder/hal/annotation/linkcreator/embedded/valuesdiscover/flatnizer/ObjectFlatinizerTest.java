package com.github.osvaldopina.linkbuilder.hal.annotation.linkcreator.embedded.valuesdiscover.flatnizer;

import org.easymock.EasyMockRule;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Rule;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class ObjectFlatinizerTest  extends EasyMockSupport {

    @Rule
    public EasyMockRule mocks = new EasyMockRule(this);

    @Mock
    private FlatnizerRegistry flatnizerRegistry;

    @Mock
    private Flatnizer anyFlatnizer;

    @Mock
    private SetFactory setFactory;

    private Set<Object> set = new HashSet<Object>();

    private Object target = new Object();

    @TestSubject
    private ObjectFlatinizer objectFlatinizer = new ObjectFlatinizer();

    @Test
    public void flatnize() throws Exception {
        expect(setFactory.create()).andReturn(set);
        expect(flatnizerRegistry.get(target)).andReturn(anyFlatnizer);
        anyFlatnizer.flatAndAddToSet(target, set);

        replayAll();

        assertThat(objectFlatinizer.flatnize(target), is(set));

        verifyAll();


    }
}