package com.github.osvaldopina.linkbuilder.example.todo;

import org.springframework.hateoas.ResourceSupport;

public class Resource extends ResourceSupport {

    public boolean isAwaysTrueProperty() {
        return true;
    }
}
