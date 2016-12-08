package com.github.osvaldopina.linkbuilder.fromcall.controllercallrecorder;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;


import com.github.osvaldopina.linkbuilder.fromcall.MethodCall;
import org.junit.Test;

import java.lang.reflect.Method;

public class RecordCallInterceptorCreatorTest {


    RecordCallInterceptorCreator recordCallInterceptorCreator = new RecordCallInterceptorCreator();

    Method method = ClassToHaveMethodCallRecorded.class.getMethod("aMethod", String.class);

    public RecordCallInterceptorCreatorTest() throws Exception {
    }


    @Test
    public void createCallRecorderForClass() throws Exception {

        SimpleCallRecorder simpleCallRecorder = new SimpleCallRecorder();

        ClassToHaveMethodCallRecorded classToHaveMethodCallRecorded = recordCallInterceptorCreator.
                createCallRecorderForClass(ClassToHaveMethodCallRecorded.class, simpleCallRecorder);

        classToHaveMethodCallRecorded.aMethod("parameter-value");

        assertThat(simpleCallRecorder.methodCall.getMethod(), is(method));
        assertThat(simpleCallRecorder.methodCall.getParams(),arrayWithSize(1));
        assertThat(simpleCallRecorder.methodCall.getParams(),arrayContainingInAnyOrder((Object) "parameter-value"));

    }



     public static class ClassToHaveMethodCallRecorded {

        public void aMethod(String param1) {

        }
    }

    public static class SimpleCallRecorder implements CallRecorder {

        public MethodCall methodCall;

        @Override
        public void record(MethodCall methodCall) {
            this.methodCall = methodCall;
        }
    }




}