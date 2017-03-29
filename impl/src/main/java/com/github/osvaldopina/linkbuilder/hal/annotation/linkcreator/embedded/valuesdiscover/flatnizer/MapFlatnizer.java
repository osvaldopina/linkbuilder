package com.github.osvaldopina.linkbuilder.hal.annotation.linkcreator.embedded.valuesdiscover.flatnizer;

import java.util.List;
import java.util.Map;

class MapFlatnizer implements Flatnizer {

    private FlatnizerRegistry flatnizerRegistry;

    MapFlatnizer(FlatnizerRegistry flatnizerRegistry) {
        this.flatnizerRegistry = flatnizerRegistry;

    }

    @Override
    public boolean canFlat(Object target) {
        return target instanceof Map;
    }

    @Override
    public void flatAndAddToSet(Object target, List<Object> objects) {
        for(Object item : ((Map<Object, Object>) target).values()) {
            flatnizerRegistry.get(item).flatAndAddToSet(item, objects);
        }
    }
}
