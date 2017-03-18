package com.github.osvaldopina.linkbuilder.example.annotation.controller.composed.link;

import com.github.osvaldopina.linkbuilder.annotation.LinkDestination;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@LinkDestination
@Retention(RetentionPolicy.RUNTIME)
public @interface MyLinkDestination {

    Destination destination();
}
