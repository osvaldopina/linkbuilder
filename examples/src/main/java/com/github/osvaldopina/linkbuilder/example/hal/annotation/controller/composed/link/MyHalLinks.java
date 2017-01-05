package com.github.osvaldopina.linkbuilder.example.hal.annotation.controller.composed.link;

import com.github.osvaldopina.linkbuilder.hal.annotation.HalLinks;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


@HalLinks
@Retention(RetentionPolicy.RUNTIME)
public @interface MyHalLinks {

    MyHalLink[] value() default {};

}
