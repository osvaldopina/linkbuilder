package com.github.osvaldopina.linkbuilder.template;

import com.github.osvaldopina.linkbuilder.LinkBuilderException;
import org.aspectj.weaver.ast.Var;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Variables {

    private List<Variable> variables;

    public Variables(List<Variable> variables) {
        this.variables = new ArrayList<Variable>(variables);
    }

    public Variables() {
        variables = new ArrayList<Variable>();
    }

    public Variable get(String name) {
        for(Variable variable:variables) {
            if (name.equals(variable.getName())){
                return variable;
            }
        }
        throw new LinkBuilderException("Could not find variable with name [" + name +"]. The variables are:" +
                getVariableNames());
    }

    public List<String> getVariableNames() {
        List<String> variableNames = new ArrayList<String>();
        for(Variable variable:variables) {
            variableNames.add(variable.getName());
        }
        return Collections.unmodifiableList(variableNames);
    }

    public Variables merge(Variables variables) {
        List<Variable> tmp = new ArrayList<Variable>();
        tmp.addAll(this.variables);
        tmp.addAll(variables.variables);
        return new Variables(tmp);
    }
}
