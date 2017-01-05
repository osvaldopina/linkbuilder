package com.github.osvaldopina.linkbuilder.example.annotation.controller.composed.link;

import com.github.osvaldopina.linkbuilder.annotation.Links;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


@Links
@Retention(RetentionPolicy.RUNTIME)
public @interface MyLinks {

    MyLink[] value() default {};

}
