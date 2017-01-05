package com.github.osvaldopina.linkbuilder.example.hal.annotation.resource.composed.link;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import com.github.osvaldopina.linkbuilder.hal.annotation.HalLink;
import com.github.osvaldopina.linkbuilder.hal.annotation.Param;


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
