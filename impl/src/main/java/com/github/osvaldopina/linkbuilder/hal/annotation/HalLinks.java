package com.github.osvaldopina.linkbuilder.hal.annotation;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface HalLinks {

    HalLink[] value() default {};


}
