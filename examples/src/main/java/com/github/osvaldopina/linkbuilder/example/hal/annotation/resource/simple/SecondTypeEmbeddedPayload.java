package com.github.osvaldopina.linkbuilder.example.hal.annotation.resource.simple;

import com.github.osvaldopina.linkbuilder.hal.annotation.HalLink;
import com.github.osvaldopina.linkbuilder.hal.annotation.HalLinks;
import com.github.osvaldopina.linkbuilder.hal.annotation.Param;
import org.springframework.hateoas.ResourceSupport;

@HalLinks({
		@HalLink(controller = RootRestController.class, overridedRel = "self" , rel = "second",
				params = {@Param(name = "name", value = "#resource.name")})
})
public class SecondTypeEmbeddedPayload extends ResourceSupport {

	private String name;

	public SecondTypeEmbeddedPayload() {
	}

	public SecondTypeEmbeddedPayload(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
