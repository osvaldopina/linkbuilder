package com.github.osvaldopina.linkbuilder.annotation;

public @interface Variable {

    String when() default "";

    String name();

    String value();
}
