package com.github.osvaldopina.linkbuilder.argumentresolver;

import com.damnhandy.uri.template.UriTemplate;
import com.github.osvaldopina.linkbuilder.argumentresolver.variablesubstitutioncontroller.VariableSubstitutionController;
import com.github.osvaldopina.linkbuilder.utils.UriTemplateAugmenter;
import org.springframework.core.MethodParameter;

import java.lang.reflect.Method;
import java.util.List;

public interface ArgumentResolver {

    boolean resolveFor(MethodParameter methodParameter);

    void augmentTemplate(UriTemplateAugmenter uriTemplateAugmenter, MethodParameter methodParameter);

    void setTemplateVariables(UriTemplate template, MethodParameter methodParameter, Object parameter,
                              VariableSubstitutionController variableSubstitutionController);


}
