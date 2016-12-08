package com.github.osvaldopina.linkbuilder.example.hal.annotation.composed.link;

import com.github.osvaldopina.linkbuilder.annotation.Link;
import com.github.osvaldopina.linkbuilder.hal.annotation.HalLink;
import com.github.osvaldopina.linkbuilder.hal.annotation.Param;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


@Retention(RetentionPolicy.RUNTIME)
@HalLink
public @interface MyHalLink {

    LINK_DESTINATION destination();

    String overridedRel() default "";

    String when() default "";

    String hreflang() default "";

    boolean templated() default false;

    Param[] params() default {};

}
