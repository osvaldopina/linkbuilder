package com.github.osvaldopina.linkbuilder.example.hal.annotation.resource.simple;

import com.github.osvaldopina.linkbuilder.hal.annotation.HalLink;
import com.github.osvaldopina.linkbuilder.hal.annotation.HalLinks;
import com.github.osvaldopina.linkbuilder.hal.annotation.Param;
import org.springframework.hateoas.ResourceSupport;

@HalLinks({
		@HalLink(controller = RootRestController.class, overridedRel = "self", rel = "first",
				params = {@Param(name = "id", value = "#resource.identity")})
})
public class FirstTypeEmbeddedPayload extends ResourceSupport {

	private int identity;

	public FirstTypeEmbeddedPayload() {
	}

	public FirstTypeEmbeddedPayload(int identity) {
		this.identity = identity;
	}

	public int getIdentity() {
		return identity;
	}

	public void setId(int identity) {
		this.identity = identity;
	}
}
