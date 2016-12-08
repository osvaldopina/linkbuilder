package com.github.osvaldopina.linkbuilder.hal.annotation;

public @interface Param {

    String when() default "";

    String name();

    String value();
}
