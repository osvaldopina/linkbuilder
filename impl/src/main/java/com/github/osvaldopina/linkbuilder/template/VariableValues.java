package com.github.osvaldopina.linkbuilder.template;

import com.github.osvaldopina.linkbuilder.LinkBuilderException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class VariableValues {

    private List<VariableValue> variableValues;

    public VariableValues(List<VariableValue> variableValues) {
        this.variableValues = new ArrayList<VariableValue>(variableValues);
    }

    public VariableValue get(String name) {
        for(VariableValue variableValue:variableValues) {
            if (name.equals(variableValue.getVariable().getName())){
                return variableValue;
            }
        }
        throw new LinkBuilderException("Could not find variable value for variable name [" + name +
                "]. The variable value names are:" +
                getVariableNames());
    }

    private List<String> getVariableNames() {
        List<String> variableNames = new ArrayList<String>();
        for(VariableValue variableValue:variableValues) {
            variableNames.add(variableValue.getVariable().getName());
        }
        return Collections.unmodifiableList(variableNames);
    }

    public List<VariableValue> getVariableValueList() {
        return Collections.unmodifiableList(variableValues);
    }


}
