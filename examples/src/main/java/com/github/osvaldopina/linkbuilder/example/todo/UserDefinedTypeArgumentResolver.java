package com.github.osvaldopina.linkbuilder.example.todo;

import com.damnhandy.uri.template.UriTemplate;
import com.github.osvaldopina.linkbuilder.argumentresolver.ArgumentResolver;
import com.github.osvaldopina.linkbuilder.argumentresolver.variablesubstitutioncontroller.VariableSubstitutionController;
import com.github.osvaldopina.linkbuilder.utils.UriTemplateAugmenter;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.List;

@Component
public class UserDefinedTypeArgumentResolver implements ArgumentResolver {
    @Override
    public boolean resolveFor(Method method, int parameterIndex) {
        return UserDefinedType.class.isAssignableFrom(method.getParameterTypes()[parameterIndex]);
    }

    @Override
    public void augmentTemplate(UriTemplateAugmenter uriTemplateAugmenter, Method method, int parameterIndex) {
        uriTemplateAugmenter.addToQuery("value1");
        uriTemplateAugmenter.addToQuery("value2");

    }

    @Override
    public void setTemplateVariables(UriTemplate template, Method method, int parameterIndex, Object parameter,
                                     VariableSubstitutionController variableSubstitutionController) {

        if (parameter != null && ((UserDefinedType) parameter).getValue1() != null) {
            template.set("value1", ((UserDefinedType) parameter).getValue1());
        }
        else {
            template.set("value1", "null-value");
        }

        if (parameter != null && ((UserDefinedType) parameter).getValue2() != null) {
            template.set("value2", ((UserDefinedType) parameter).getValue2());
        }
        else {
            template.set("value2", "null-value");
        }
    }

}
