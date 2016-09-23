package com.github.osvaldopina.linkbuilder.annotation;

public @interface Param {

    String when() default "";

    String name();

    String value();
}
