package com.github.osvaldopina.linkbuilder.hal.annotation.linkcreator.embedded.valuesdiscover.flatnizer;

import java.util.ArrayList;
import java.util.List;

public class ListFactory {

    public static final ListFactory INSTANCE = new ListFactory();

    List<Object> create() {
        return new ArrayList<Object>();
    }
}
