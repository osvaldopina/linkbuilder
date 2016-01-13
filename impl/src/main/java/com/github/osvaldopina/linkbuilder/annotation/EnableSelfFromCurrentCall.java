package com.github.osvaldopina.linkbuilder.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by deinf.osvaldo on 16/12/2015.
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@GenerateUriTemplateFor
public @interface EnableSelfFromCurrentCall {
}
