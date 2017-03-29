package com.github.osvaldopina.linkbuilder.expression.springhateoas.impl;


import com.github.osvaldopina.linkbuilder.expression.springhateoas.ExpressionHandler;


public abstract class BaseExpressionHandler implements ExpressionHandler {

    @Override
    public final int compareTo(ExpressionHandler other) {
        return getPriority() - other.getPriority();
    }
}