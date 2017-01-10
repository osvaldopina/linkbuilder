package com.github.osvaldopina.linkbuilder.hal.annotation.linkcreator.embedded.valuesdiscover.flatnizer;

import java.util.Set;

interface Flatnizer {

    boolean canFlat(Object target);

    void flatAndAddToSet(Object target, Set<Object> objects);

}
