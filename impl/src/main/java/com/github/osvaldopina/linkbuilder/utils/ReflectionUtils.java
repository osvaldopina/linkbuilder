package com.github.osvaldopina.linkbuilder.utils;

import java.lang.reflect.InvocationTargetException;

import com.github.osvaldopina.linkbuilder.LinkBuilderException;

public class ReflectionUtils {

    public static final ReflectionUtils INSTANCE = new ReflectionUtils();

    private Utils utils = Utils.INSTANCE;


    private ReflectionUtils() {

    }

    public <E> E callMethod(Class<E> type, Object target, String methodName, Object... args) {
        try {
            return (E) target.getClass().getMethod(methodName).invoke(target);
        } catch (IllegalAccessException e) {
            throw new IllegalStateException("Could not call Method " + methodName + " on " + target + " because " + e, e);
        } catch (InvocationTargetException e) {
            throw new IllegalStateException("Could not call Method " + methodName + " on " + target + " because " + e, e);
        } catch (NoSuchMethodException e) {
            throw new IllegalStateException("Could not call Method " + methodName + " on " + target + " because " + e, e);
        }
    }

    public boolean hasMethod(Object target, String methodName, Object... args) {
        try {
            target.getClass().getMethod(methodName);
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


    public Class<?> getMethodReturnType(Object target, String methodName, Object... args) {
        try {
            return target.getClass().getMethod(methodName).getReturnType();
        } catch (NoSuchMethodException e) {
            throw new IllegalStateException("Could not find method " + methodName + " on " + target + " because " + e, e);
        }
    }

    public boolean methodReturnTypeIs(Class<?> targetType, Object target, String methodName, Object ...args) {
        Class<?> returnType = getMethodReturnType(target, methodName, args);
        return targetType.isAssignableFrom(returnType);
    }


}
