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

    String relation() default "";

    String when() default "";

    boolean templated() default false;

    Param[] params() default {};

}
