package com.github.osvaldopina.linkbuilder.template.generation.springhateoas;

import java.lang.reflect.Method;
import java.util.List;

import com.damnhandy.uri.template.UriTemplate;
import com.github.osvaldopina.linkbuilder.template.Template;
import com.github.osvaldopina.linkbuilder.template.Variable;
import com.github.osvaldopina.linkbuilder.template.VariableType;
import com.github.osvaldopina.linkbuilder.template.Variables;
import org.springframework.hateoas.core.AnnotationMappingDiscoverer;
import org.springframework.web.bind.annotation.RequestMapping;

public class MethodTemplateCreator {

	public static final MethodTemplateCreator INSTANCE = new MethodTemplateCreator();


	private AnnotationMappingDiscoverer annotationMappingDiscoverer = new AnnotationMappingDiscoverer(RequestMapping.class);

	MethodTemplateCreator() {

	}


	public Template create(Method method, Variables variables) {
		String path = annotationMappingDiscoverer.getMapping(method);

		UriTemplateAugmenter uriTemplateAugmenter = new UriTemplateAugmenter(UriTemplate.buildFromTemplate(path));


		List<Variable> variableList = variables.getVariableList();
		boolean firstParam = true;
		Variable variable;
		for(int i=0; i< variableList.size(); i++) {
			variable = variableList.get(i);
			if (variable.getVariableType() == VariableType.QUERY) {
				uriTemplateAugmenter.addToQuery(variable.getName());
			}
		}

		return new Template(uriTemplateAugmenter.getUriTemplate(), variables);
	}


}
