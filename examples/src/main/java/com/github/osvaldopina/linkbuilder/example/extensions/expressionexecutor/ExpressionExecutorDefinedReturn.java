package com.github.osvaldopina.linkbuilder.example.extensions.expressionexecutor;

import com.github.osvaldopina.linkbuilder.expression.ExpressionExecutor;

public class ExpressionExecutorDefinedReturn implements ExpressionExecutor {

    public static boolean returnValue = false;

    @Override
    public boolean isTrue(String expression, Object payload, Object[] params) {
        return returnValue;
    }

    @Override
    public Object getValue(String expression, Object payload, Object[] params) {
        throw new IllegalStateException("Should not be called");
    }

}
