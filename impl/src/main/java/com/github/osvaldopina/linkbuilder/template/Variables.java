package com.github.osvaldopina.linkbuilder.template;

import com.github.osvaldopina.linkbuilder.LinkBuilderException;

import java.util.*;

public class Variables {

    private List<Variable> variableList;
    private Map<String,Variable> namedVariables;

    Variables(List<Variable> variableList) {
        this.variableList = new ArrayList<Variable>(variableList);
        namedVariables = new HashMap<String, Variable>();
        for(Variable variable:variableList) {
            namedVariables.put(variable.getName(), variable);
        }
    }

    Variables() {
        variableList = new ArrayList<Variable>();
        namedVariables = new HashMap<String, Variable>();
    }

    public Variable get(String name) {
        Variable variable = namedVariables.get(name);
        if (variable == null) {
            throw new LinkBuilderException("Could not find variable with name [" + name + "]. The variables are:" +
                    getVariableNames());
        }
        else {
            return variable;
        }
    }


    public List<Variable> getVariableList() {
        return Collections.unmodifiableList(variableList);
    }

    private List<String> getVariableNames() {
        List<String> variableNames = new ArrayList<String>();
        for(Variable variable: variableList) {
            variableNames.add(variable.getName());
        }
        return Collections.unmodifiableList(variableNames);
    }
}
