package com.github.osvaldopina.linkbuilder.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Link {

    Class<?> destination();

    String target();

    String relation();

    String when();

    boolean templated() default false;

    LinkParam[] params() default {};

}
