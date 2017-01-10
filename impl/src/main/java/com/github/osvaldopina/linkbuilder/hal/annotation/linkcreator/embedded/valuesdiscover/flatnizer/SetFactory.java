package com.github.osvaldopina.linkbuilder.hal.annotation.linkcreator.embedded.valuesdiscover.flatnizer;

import java.util.HashSet;
import java.util.Set;

public class SetFactory {

    public static final SetFactory INSTANCE = new SetFactory();

    Set<Object> create() {
        return new HashSet<Object>();
    }
}
