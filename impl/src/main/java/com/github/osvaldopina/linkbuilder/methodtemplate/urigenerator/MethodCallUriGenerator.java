package com.github.osvaldopina.linkbuilder.methodtemplate.urigenerator;

import com.github.osvaldopina.linkbuilder.argumentresolver.variablesubstitutioncontroller.VariableSubstitutionController;
import com.github.osvaldopina.linkbuilder.methodtemplate.MethodCall;

public interface MethodCallUriGenerator {

    String generate(MethodCall methodCall, boolean templated);

    String generate(MethodCall methodCall,
                    boolean templated,
                    VariableSubstitutionController variableSubstitutionController);


}
