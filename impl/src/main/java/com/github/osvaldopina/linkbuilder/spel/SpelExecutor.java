package com.github.osvaldopina.linkbuilder.spel;

import org.springframework.hateoas.ResourceSupport;

public interface SpelExecutor {

    boolean isTrue(String expression, Object payload, Object[] params);

    Object getValue(String expression, Object payload, Object[] params);
}
