package com.github.osvaldopina.linkbuilder.expression;

public interface ExpressionExecutor {

    boolean isTrue(String expression, Object payload, Object[] params);

    Object getValue(String expression, Object payload, Object[] params);
}
