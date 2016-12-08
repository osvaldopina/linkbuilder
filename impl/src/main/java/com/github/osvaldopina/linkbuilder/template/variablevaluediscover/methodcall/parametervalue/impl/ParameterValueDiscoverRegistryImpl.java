package com.github.osvaldopina.linkbuilder.template.variablevaluediscover.methodcall.parametervalue.impl;

import com.github.osvaldopina.linkbuilder.LinkBuilderException;
import com.github.osvaldopina.linkbuilder.fromcall.MethodCall;
import com.github.osvaldopina.linkbuilder.template.Variables;
import com.github.osvaldopina.linkbuilder.template.variablevaluediscover.methodcall.parametervalue.ParameterVariableValueDiscover;
import com.github.osvaldopina.linkbuilder.template.variablevaluediscover.methodcall.parametervalue.ParameterVariableValueDiscoverRegistry;

import java.util.ArrayList;
import java.util.List;

public class ParameterValueDiscoverRegistryImpl implements ParameterVariableValueDiscoverRegistry{

    private List<ParameterVariableValueDiscover> parameterVariableValueDiscovers =
            new ArrayList<ParameterVariableValueDiscover>();

    public ParameterValueDiscoverRegistryImpl(List<ParameterVariableValueDiscover> parameterVariableValueDiscovers) {
        this.parameterVariableValueDiscovers.addAll(parameterVariableValueDiscovers);
    }

    @Override
    public ParameterVariableValueDiscover get(Variables variables, MethodCall methodCall,
                                              Object payload, int parameterIndex) {

        for(ParameterVariableValueDiscover parameterVariableValueDiscover:parameterVariableValueDiscovers) {
            if (parameterVariableValueDiscover.canDiscover(methodCall, parameterIndex)) {
                return parameterVariableValueDiscover;
            }
        }
        throw new LinkBuilderException("Could not find a ParameterVariableValueDiscover for parameter #" +
                parameterIndex + " in method " + methodCall.getMethod());
    }
}
