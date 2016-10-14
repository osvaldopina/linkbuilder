package com.github.osvaldopina.linkbuilder.methodtemplate.urigenerator;

import java.lang.reflect.Method;

public interface AnnotatedMethodUriGenerators {

    AnnotatedMethodUriGenerator getAnnotatedMethodUriGenerator(Method method);


}
