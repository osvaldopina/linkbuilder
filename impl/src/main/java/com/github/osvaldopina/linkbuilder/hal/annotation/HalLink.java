package com.github.osvaldopina.linkbuilder.hal.annotation;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface HalLink {

    String destination() default "";

    Class<?> controller() default NullDestination.class;

    String rel() default "";

    String overridedRel() default "";

    String when() default "";

    boolean templated() default false;

    String hreflang() default "";

    Variable[] variables() default {};

    class NullDestination {

    }
}
