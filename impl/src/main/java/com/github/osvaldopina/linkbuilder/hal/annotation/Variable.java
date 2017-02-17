package com.github.osvaldopina.linkbuilder.hal.annotation;

public @interface Variable {

    String when() default "";

    String name();

    String value();
}
