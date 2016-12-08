package com.github.osvaldopina.linkbuilder.urigeneration.link.annotation;

import java.lang.reflect.Method;

public interface AnnotatedMethodUriGenerators {

    AnnotatedMethodUriGenerator getAnnotatedMethodUriGenerator(Method method);


}
