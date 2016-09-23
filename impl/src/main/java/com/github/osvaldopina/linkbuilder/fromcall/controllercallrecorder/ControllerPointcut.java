package com.github.osvaldopina.linkbuilder.fromcall.controllercallrecorder;

import org.springframework.aop.ClassFilter;
import org.springframework.aop.support.StaticMethodMatcherPointcut;

import java.lang.reflect.Method;

public class ControllerPointcut extends StaticMethodMatcherPointcut {

    public boolean matches(Method method, Class cls) {
        return true;
    }

    public ClassFilter getClassFilter() {
        return new ClassFilter() {
            public boolean matches(Class cls) {
                return true;
            }
        };

    }
}
