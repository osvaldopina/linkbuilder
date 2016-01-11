package org.halhelper.linkbuilder.controllerproxy.controllercall;

import org.springframework.aop.ClassFilter;
import org.springframework.aop.support.StaticMethodMatcherPointcut;

import java.lang.reflect.Method;

/**
 * Created by deinf.osvaldo on 15/12/2015.
 */
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
