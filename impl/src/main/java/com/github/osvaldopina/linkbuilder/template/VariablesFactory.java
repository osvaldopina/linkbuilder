package com.github.osvaldopina.linkbuilder.template;

import java.util.ArrayList;
import java.util.List;

public class VariablesFactory {

	public static final VariablesFactory INSTANCE = new VariablesFactory();

	VariablesFactory() {

	}

	public Variables create() {
		return new Variables();
	}

	public Variables create(List<Variable> variableList) {
		return new Variables(variableList);
	}

	public Variables merge(Variables one, Variables other) {
		List<Variable> variableList = new ArrayList<Variable>(one.getVariableList());
		variableList.addAll(other.getVariableList());
		return new Variables(variableList);
	}
}
