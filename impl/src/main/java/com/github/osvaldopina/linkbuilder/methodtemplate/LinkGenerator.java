package com.github.osvaldopina.linkbuilder.methodtemplate;

import com.github.osvaldopina.linkbuilder.argumentresolver.variablesubstitutioncontroller.VariableSubstitutionController;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;

public interface LinkGenerator {

    Link generate(MethodCall methodCall, boolean templated, String rel);

    Link generate(MethodCall methodCall, boolean templated, String rel,
                         VariableSubstitutionController variableSubstitutionController);

    Link generate(com.github.osvaldopina.linkbuilder.annotation.Link linkAnnotation, ResourceSupport payLoad,
                         Object[] params);

}
