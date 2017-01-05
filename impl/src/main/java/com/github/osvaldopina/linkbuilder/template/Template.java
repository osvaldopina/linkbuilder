package com.github.osvaldopina.linkbuilder.template;

import com.damnhandy.uri.template.UriTemplate;
import com.github.osvaldopina.linkbuilder.template.impl.UriTemplateVariableSetter;

public class Template {

    private UriTemplate uriTemplate;

    private Variables variables;

    private UriTemplateVariableSetter uriTemplateVariableSetter = UriTemplateVariableSetter.INSTANCE;

    public Template(UriTemplate uriTemplate, Variables variables) {
        this.uriTemplate = uriTemplate;
        this.variables = variables;
    }


    public String toUri(VariableValues variableValues) {
        return uriTemplateVariableSetter.createNewTemplateAndSetVariables(
                uriTemplate,
                variables,
                variableValues
        ).expand();
    }

    public String toTemplatedUri(VariableValues variableValues) {
        return uriTemplateVariableSetter.createNewTemplateAndSetVariables(
                uriTemplate,
                variables,
                variableValues
        ).expandPartial();
    }

    public Variables getVariables() {
        return variables;
    }

    public UriTemplate getUriTemplate() {
        return uriTemplate;
    }


}
