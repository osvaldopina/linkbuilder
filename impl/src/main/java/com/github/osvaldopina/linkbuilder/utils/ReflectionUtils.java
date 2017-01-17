package com.github.osvaldopina.linkbuilder.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.github.osvaldopina.linkbuilder.LinkBuilder;
import com.github.osvaldopina.linkbuilder.LinkBuilderException;

public class ReflectionUtils {

    public static final ReflectionUtils INSTANCE = new ReflectionUtils();

    private Utils utils = Utils.INSTANCE;


    ReflectionUtils() {

    }

    public <E> E callMethod(Class<E> type, Object target, String methodName, Object... args) {
        try {
            Method method =  target.getClass().getMethod(methodName, getArgsTypes(args));
            return (E) method.invoke(target, args);
        } catch (IllegalAccessException e) {
            throw new LinkBuilderException("Could not call Method " + methodName + " on " + target + " because " + e, e);
        } catch (InvocationTargetException e) {
            throw new LinkBuilderException("Could not call Method " + methodName + " on " + target + " because " + e, e);
        } catch (NoSuchMethodException e) {
            throw new LinkBuilderException("Could not call Method " + methodName + " on " + target + " because " + e, e);
        }
    }

    public boolean hasMethod(Object target, String methodName, Class<?> ... argsTypes) {
        try {
            target.getClass().getMethod(methodName, argsTypes);
            return true;
        } catch (NoSuchMethodException e) {
            return false;
        }
    }

    public boolean hasValue(Object target, String methodName, Object... args) {
        try {
            if (hasMethod(target, methodName)) {
                Object value = callMethod(Object.class, target, methodName, args);
                return utils.isPresent(value);
            }
            else {
                return false;
            }
        } catch (LinkBuilderException e) {
            return false;
        }
    }

    public boolean hasEmptyValue(Object target, String methodName, Object... args) {
        return ! hasValue(target, methodName, args);
    }


    private Class<?>[] getArgsTypes(Object[] args) {
        Class<?>[] argTypes = new Class<?>[args.length];
        for(int i=0; i< args.length; i++) {
            argTypes[i] = args[i].getClass();
        }
        return argTypes;
    }



}
