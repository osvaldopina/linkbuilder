package com.github.osvaldopina.linkbuilder.hal.annotation.linkcreator.embedded.valuesdiscover.flatnizer;

import java.util.List;

public class ObjectFlatinizer {

    public static final ObjectFlatinizer INSTANCE = new ObjectFlatinizer();

    private FlatnizerRegistry flatnizerRegistry = FlatnizerRegistry.INSTANCE;

    private ListFactory listFactory = ListFactory.INSTANCE;

    public List<Object> flatnize(Object target) {
        List<Object> values = listFactory.create();
        flatnizerRegistry.get(target).flatAndAddToSet(target, values);
        return values;
    }


}
