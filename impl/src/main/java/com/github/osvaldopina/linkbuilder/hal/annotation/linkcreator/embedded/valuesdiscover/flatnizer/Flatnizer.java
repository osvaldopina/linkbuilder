package com.github.osvaldopina.linkbuilder.hal.annotation.linkcreator.embedded.valuesdiscover.flatnizer;

import java.util.List;

interface Flatnizer {

    boolean canFlat(Object target);

    void flatAndAddToSet(Object target, List<Object> objects);

}
