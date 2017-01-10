package com.github.osvaldopina.linkbuilder.hal.annotation.linkcreator.embedded.valuesdiscover.flatnizer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class FlatnizerRegistry {

    public static final FlatnizerRegistry INSTANCE = new FlatnizerRegistry();

    private List<Flatnizer> flatnizers = new ArrayList<Flatnizer>();

    private EmptyFlatnizer emptyFlatnizer = EmptyFlatnizer.INSTANCE;

    FlatnizerRegistry() {
        flatnizers.add(new CollectionFlatnizer(this));
        flatnizers.add(new MapFlatnizer(this));
    }

    FlatnizerRegistry(List<Flatnizer> flatnizers, EmptyFlatnizer emptyFlatnizer) {
        this.flatnizers.addAll(flatnizers);
        this.emptyFlatnizer = emptyFlatnizer;
    }

    public Flatnizer get(Object target) {
        for(Flatnizer flatnizer: flatnizers) {
            if (flatnizer.canFlat(target)) {
                return flatnizer;
            }
        }
        return emptyFlatnizer;
    }
}
