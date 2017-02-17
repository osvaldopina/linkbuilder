package com.github.osvaldopina.linkbuilder.example.annotation.resource.composed.link;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import com.github.osvaldopina.linkbuilder.annotation.Link;
import com.github.osvaldopina.linkbuilder.annotation.Variable;

@Link
@Retention(RetentionPolicy.RUNTIME)
public @interface MyLink {

    Destionation destination();

    String overridedRel() default "";

    String when() default "";

    boolean templated() default false;

    Variable[] variables() default {};

}
