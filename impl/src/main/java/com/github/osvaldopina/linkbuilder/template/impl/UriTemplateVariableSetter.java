package com.github.osvaldopina.linkbuilder.template.impl;

import com.damnhandy.uri.template.UriTemplate;
import com.github.osvaldopina.linkbuilder.template.Variable;
import com.github.osvaldopina.linkbuilder.template.VariableValue;
import com.github.osvaldopina.linkbuilder.template.VariableValues;
import com.github.osvaldopina.linkbuilder.template.Variables;

public class UriTemplateVariableSetter {

    public UriTemplate createNewTemplateAndSetVariables(
            UriTemplate uriTemplate,
            Variables variables,
            VariableValues variableValues) {
        UriTemplate newUriTemplate = UriTemplate.fromTemplate(uriTemplate).build();
        for(VariableValue variableValue: variableValues.getVariableValueList()) {
                newUriTemplate.set(variableValue.getVariable().getName(), variableValue.getValue());
        }
        return newUriTemplate;
    }

}
