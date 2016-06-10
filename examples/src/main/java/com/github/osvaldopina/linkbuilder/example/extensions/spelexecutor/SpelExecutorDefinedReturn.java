package com.github.osvaldopina.linkbuilder.example.extensions.spelexecutor;

import com.github.osvaldopina.linkbuilder.spel.SpelExecutor;

public class SpelExecutorDefinedReturn implements SpelExecutor {

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
