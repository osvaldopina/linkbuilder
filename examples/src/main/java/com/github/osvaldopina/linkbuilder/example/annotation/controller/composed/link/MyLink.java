package com.github.osvaldopina.linkbuilder.example.annotation.controller.composed.link;

import com.github.osvaldopina.linkbuilder.annotation.Link;
import com.github.osvaldopina.linkbuilder.annotation.Variable;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Link
@Retention(RetentionPolicy.RUNTIME)
public @interface MyLink {

    Destination destination();

    String overridedRel() default "";

    String when() default "";

    boolean templated() default false;

    Variable[] variables() default {};

}
