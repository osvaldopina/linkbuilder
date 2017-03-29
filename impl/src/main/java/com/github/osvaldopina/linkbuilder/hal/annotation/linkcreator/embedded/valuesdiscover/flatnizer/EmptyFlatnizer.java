package com.github.osvaldopina.linkbuilder.hal.annotation.linkcreator.embedded.valuesdiscover.flatnizer;

import java.util.List;

import com.github.osvaldopina.linkbuilder.LinkBuilderException;

class EmptyFlatnizer implements Flatnizer{

    public static final EmptyFlatnizer INSTANCE = new EmptyFlatnizer();

    @Override
    public boolean canFlat(Object target) {
        throw new LinkBuilderException("internal error! Could not be called!");
    }

    @Override
    public void flatAndAddToSet(Object target, List<Object> objects) {
       objects.add(target);
    }
}
