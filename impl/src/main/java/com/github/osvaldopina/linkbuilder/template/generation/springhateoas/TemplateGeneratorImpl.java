package com.github.osvaldopina.linkbuilder.template.generation.springhateoas;

import com.damnhandy.uri.template.Expression;
import com.damnhandy.uri.template.UriTemplate;
import com.damnhandy.uri.template.UriTemplateBuilder;
import com.damnhandy.uri.template.UriTemplateComponent;
import com.damnhandy.uri.template.impl.Modifier;
import com.damnhandy.uri.template.impl.Operator;
import com.damnhandy.uri.template.impl.VarSpec;
import com.github.osvaldopina.linkbuilder.template.Template;
import com.github.osvaldopina.linkbuilder.template.Variable;
import com.github.osvaldopina.linkbuilder.template.VariableType;
import com.github.osvaldopina.linkbuilder.template.Variables;
import com.github.osvaldopina.linkbuilder.template.generation.TemplateGenerator;
import com.github.osvaldopina.linkbuilder.template.generation.argumentresolver.ArgumentResolver;
import com.github.osvaldopina.linkbuilder.template.generation.argumentresolver.ArgumentResolverRegistry;
import com.github.osvaldopina.linkbuilder.utils.UriTemplateAugmenter;
import org.springframework.core.MethodParameter;
import org.springframework.hateoas.core.AnnotationMappingDiscoverer;
import org.springframework.hateoas.core.MethodParameters;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.reflect.Method;
import java.util.List;

public class TemplateGeneratorImpl implements TemplateGenerator{

    private static final AnnotationMappingDiscoverer REQUEST_MAPPING_DISCOVER =
            new AnnotationMappingDiscoverer(RequestMapping.class);

    private ArgumentResolverRegistry argumentResolverRegistry;

    public TemplateGeneratorImpl(ArgumentResolverRegistry argumentResolverRegistry) {
        this.argumentResolverRegistry = argumentResolverRegistry;
    }

    @Override
    public Template generate(Method method) {


        String path = REQUEST_MAPPING_DISCOVER.getMapping(method);
        UriTemplateBuilder uriTemplateBuilder = UriTemplate.buildFromTemplate(path);

        ArgumentResolver argumentResolver;
        Variables variables = new Variables();
        Variables methodVariables;
        for(int i=0; i < method.getParameterTypes().length; i++) {
            argumentResolver = argumentResolverRegistry.getArgumentResolver(method, i);
            methodVariables = argumentResolver.create(method, i);
            if (methodVariables != null) {
                variables = variables.merge(methodVariables);
            }
        }
        List<String> variableNames = variables.getVariableNames();
        boolean firstParam = true;
        Variable variable;
        for(int i=0; i< variableNames.size(); i++) {
            variable = variables.get(variableNames.get(i));
            if (variable.getVariableType() == VariableType.QUERY) {
                Expression expression = getQueryComponent(uriTemplateBuilder);
                if (expression == null) {
                    uriTemplateBuilder.query(variable.getName());
                }
                else {
                    expression.getVarSpecs().add(new VarSpec(variable.getName(), Modifier.NONE));
                }
            }
        }

        return new Template(uriTemplateBuilder.build(), variables);
    }

    private Expression getQueryComponent(UriTemplateBuilder uriTemplateBuilder) {
        for(UriTemplateComponent templateComponent : uriTemplateBuilder.getComponents()) {
            if (templateComponent instanceof Expression && ((Expression) templateComponent).getOperator() == Operator.QUERY) {
                return (Expression) templateComponent;
            }
        }
        return null;
    }
}
