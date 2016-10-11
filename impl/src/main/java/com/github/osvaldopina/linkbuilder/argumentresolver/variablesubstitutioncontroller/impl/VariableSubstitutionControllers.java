package com.github.osvaldopina.linkbuilder.argumentresolver.variablesubstitutioncontroller.impl;

import com.github.osvaldopina.linkbuilder.argumentresolver.variablesubstitutioncontroller.VariableSubstitutionController;
import org.springframework.core.MethodParameter;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class VariableSubstitutionControllers implements VariableSubstitutionController {

    List<VariableSubstitutionController> variableSubstitutionControllers = new ArrayList<VariableSubstitutionController>();

    @Override
    public boolean substitute(Method method, int parameterIndex, String variableName, Object parameterValue) {
    for(VariableSubstitutionController variableSubstitutionController:variableSubstitutionControllers) {
            if (!variableSubstitutionController.substitute(method, parameterIndex, variableName, parameterValue)) {
                return false;
            }
        }
        return true;
    }

    public void addVariableSubstitutionControllerAggregator(
            VariableSubstitutionController variableSubstitutionController) {

        variableSubstitutionControllers.add(variableSubstitutionController);
    }

    public boolean hasVariableSubstitutionController() {
        return variableSubstitutionControllers.size() > 0;
    }
}
