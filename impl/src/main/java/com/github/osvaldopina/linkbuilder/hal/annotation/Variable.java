package com.github.osvaldopina.linkbuilder.hal.annotation;

import java.lang.annotation.*;

@Target({ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Variable {

    String when() default "";

    String name();

    String value();
}
