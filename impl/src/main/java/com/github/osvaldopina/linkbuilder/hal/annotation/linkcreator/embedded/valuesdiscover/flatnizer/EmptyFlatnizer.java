package com.github.osvaldopina.linkbuilder.hal.annotation.linkcreator.embedded.valuesdiscover.flatnizer;

import com.github.osvaldopina.linkbuilder.LinkBuilderException;

import java.util.Set;

class EmptyFlatnizer implements Flatnizer{

    public static final EmptyFlatnizer INSTANCE = new EmptyFlatnizer();

    @Override
    public boolean canFlat(Object target) {
        throw new LinkBuilderException("internal error! Could not be called!");
    }

    @Override
    public void flatAndAddToSet(Object target, Set<Object> objects) {
       objects.add(target);
    }
}
