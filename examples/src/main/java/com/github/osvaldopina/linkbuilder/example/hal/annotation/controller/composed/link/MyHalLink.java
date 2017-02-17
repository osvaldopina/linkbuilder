package com.github.osvaldopina.linkbuilder.example.hal.annotation.controller.composed.link;

import com.github.osvaldopina.linkbuilder.hal.annotation.HalLink;
import com.github.osvaldopina.linkbuilder.hal.annotation.Variable;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


@Retention(RetentionPolicy.RUNTIME)
@HalLink
public @interface MyHalLink {

    Destination destination();

    String overridedRel() default "";

    String when() default "";

    String hreflang() default "";

    boolean templated() default false;

    Variable[] variables() default {};

}
