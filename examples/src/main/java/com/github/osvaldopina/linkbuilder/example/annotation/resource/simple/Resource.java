package com.github.osvaldopina.linkbuilder.example.annotation.resource.simple;

import com.github.osvaldopina.linkbuilder.annotation.Link;
import com.github.osvaldopina.linkbuilder.annotation.Links;
import com.github.osvaldopina.linkbuilder.annotation.Variable;
import org.springframework.hateoas.ResourceSupport;

@Links({
		@Link(controller = RootRestController.class, rel = "root", overridedRel = "self"),
		@Link(controller = RootRestController.class, rel = "direct-link",
				variables = {
						@Variable(name = "query", value = "#resource.queryValue"),
						@Variable(name = "path", value = "#resource.pathValue")
				}),
		@Link(controller = RootRestController.class, rel = "direct-link", overridedRel = "direct-link-overrided",
				variables = {
						@Variable(name = "query", value = "#resource.queryValue"),
						@Variable(name = "path", value = "#resource.pathValue")
				}),
		@Link(controller = RootRestController.class,
				rel = "direct-link-templated",
				templated = true,
				variables = {
						@Variable(name = "templated", value = "'templated-value'")
				})
})
public class Resource extends ResourceSupport {

	private String queryValue;

	private String pathValue;

	public String getQueryValue() {
		return queryValue;
	}

	public void setQueryValue(String queryValue) {
		this.queryValue = queryValue;
	}

	public String getPathValue() {
		return pathValue;
	}

	public void setPathValue(String pathValue) {
		this.pathValue = pathValue;
	}
}
