package com.github.osvaldopina.linkbuilder.argumentresolver;

import com.damnhandy.uri.template.UriTemplate;
import com.github.osvaldopina.linkbuilder.argumentresolver.variablesubstitutioncontroller.VariableSubstitutionController;
import com.github.osvaldopina.linkbuilder.utils.UriTemplateAugmenter;
import org.springframework.core.MethodParameter;

import java.lang.reflect.Method;
import java.util.List;

public interface ArgumentResolver {

    boolean resolveFor(Method method, int parameterIndex);

    void augmentTemplate(UriTemplateAugmenter uriTemplateAugmenter, Method method, int parameterIndex);

    void setTemplateVariables(UriTemplate template, Method method, int parameterIndex, Object parameter,
                              VariableSubstitutionController variableSubstitutionController);


}
