package com.github.osvaldopina.linkbuilder.hal.annotation.linkcreator.embedded.valuesdiscover.flatnizer;

import java.util.HashSet;
import java.util.Set;

public class ObjectFlatinizer {

    public static final ObjectFlatinizer INSTANCE = new ObjectFlatinizer();

    private FlatnizerRegistry flatnizerRegistry = FlatnizerRegistry.INSTANCE;

    private SetFactory setFactory = SetFactory.INSTANCE;

    public Set<Object> flatnize(Object target) {
        Set<Object> values = setFactory.create();
        flatnizerRegistry.get(target).flatAndAddToSet(target, values);
        return values;
    }


}
