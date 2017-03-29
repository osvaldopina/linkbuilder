package com.github.osvaldopina.linkbuilder.hal.annotation.linkcreator.embedded.valuesdiscover.flatnizer;

import java.util.Collection;
import java.util.List;

class CollectionFlatnizer implements Flatnizer {

    private FlatnizerRegistry flatnizerRegistry;

    CollectionFlatnizer(FlatnizerRegistry flatnizerRegistry) {
        this.flatnizerRegistry = flatnizerRegistry;

    }

    @Override
    public boolean canFlat(Object target) {
        return target instanceof Collection;
    }

    @Override
    public void flatAndAddToSet(Object target, List<Object> objects) {
        for(Object item : ((Collection<Object>) target)) {
            flatnizerRegistry.get(item).flatAndAddToSet(item, objects);
        }
    }
}
