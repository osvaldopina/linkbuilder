package com.github.osvaldopina.linkbuilder.example.hal.annotation.resource.composed.link;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import com.github.osvaldopina.linkbuilder.hal.annotation.HalLinks;


@HalLinks
@Retention(RetentionPolicy.RUNTIME)
public @interface MyHalLinks {

    MyHalLink[] value() default {};

}
