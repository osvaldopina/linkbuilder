package com.github.osvaldopina.linkbuilder.hal.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
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
