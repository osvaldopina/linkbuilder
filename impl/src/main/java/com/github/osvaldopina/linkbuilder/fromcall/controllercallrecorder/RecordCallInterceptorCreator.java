package com.github.osvaldopina.linkbuilder.fromcall.controllercallrecorder;

import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.BeanUtils;

public class RecordCallInterceptorCreator {

    public static final RecordCallInterceptorCreator INSTANCE = new RecordCallInterceptorCreator();

    protected RecordCallInterceptorCreator() {

    }

    public <T> T createCallRecorderForClass(Class<T> clazz, CallRecorder callRecorder) {

        T controller = BeanUtils.instantiate(clazz);

        ProxyFactory factory = new ProxyFactory();
        factory.addAdvice(new RecordCallInterceptor(callRecorder));
        factory.setTarget(controller);
        return (T) factory.getProxy();

    }

}
