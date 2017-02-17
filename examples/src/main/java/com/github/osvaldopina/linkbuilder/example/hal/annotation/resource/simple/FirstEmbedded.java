package com.github.osvaldopina.linkbuilder.example.hal.annotation.resource.simple;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.osvaldopina.linkbuilder.hal.annotation.HalLink;
import com.github.osvaldopina.linkbuilder.hal.annotation.HalLinks;
import com.github.osvaldopina.linkbuilder.hal.annotation.Variable;
import org.springframework.hateoas.ResourceSupport;

@HalLinks({
		@HalLink(controller = RootRestController.class, overridedRel = "self", rel = "first",
				variables = {@Variable(name = "id", value = "#resource.identity")})
})
public class FirstEmbedded extends ResourceSupport {

	private int identity;

	private ThirdEmbedded thirdEmbedded;

	public FirstEmbedded() {
	}

	public FirstEmbedded(int identity) {
		this.identity = identity;
	}

	public int getIdentity() {
		return identity;
	}

	public void setId(int identity) {
		this.identity = identity;
	}

	@JsonProperty("_embedded")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	public ThirdEmbedded getEmbedded() {
		return thirdEmbedded;
	}

	public void setThirdEmbedded(ThirdEmbedded thirdEmbedded) {
		this.thirdEmbedded = thirdEmbedded;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		if (!super.equals(o)) return false;

		FirstEmbedded that = (FirstEmbedded) o;

		return identity == that.identity;

	}

	@Override
	public int hashCode() {
		int result = super.hashCode();
		result = 31 * result + identity;
		return result;
	}
}
