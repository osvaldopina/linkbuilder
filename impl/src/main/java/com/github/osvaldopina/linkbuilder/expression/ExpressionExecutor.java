package com.github.osvaldopina.linkbuilder.expression;

public interface ExpressionExecutor {

    boolean isTrue(String expression, Object resource, Object[] params);

    Object getValue(String expression, Object resource, Object[] params);
}
