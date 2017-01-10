package com.github.osvaldopina.linkbuilder.hal.annotation.linkcreator.embedded.propertyreader;

import com.github.osvaldopina.linkbuilder.LinkBuilderException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MethodChain {

    private List<Method> methods;

    public MethodChain(List<Method> methods) {
        this.methods = new ArrayList<Method>(methods);
    }

    public Object execCalls(Object target) {
        Object currentTarget = target;
        for(Method method: methods) {
            try {
                currentTarget = method.invoke(currentTarget);
                if (currentTarget == null) {
                    return null;
                }
            } catch (IllegalAccessException e) {
                throw new LinkBuilderException("Internal Error! "+e,e);
            } catch (InvocationTargetException e) {
                throw new LinkBuilderException("Internal Error! "+e,e);
            }
        }
        return currentTarget;
    }

    public List<Method> getMethods() {
        return Collections.unmodifiableList(methods);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MethodChain that = (MethodChain) o;

        return methods != null ? methods.equals(that.methods) : that.methods == null;

    }

    @Override
    public int hashCode() {
        return methods != null ? methods.hashCode() : 0;
    }
}
