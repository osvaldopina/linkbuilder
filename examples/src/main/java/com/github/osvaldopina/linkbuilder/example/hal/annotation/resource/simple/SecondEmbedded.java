package com.github.osvaldopina.linkbuilder.example.hal.annotation.resource.simple;

import com.github.osvaldopina.linkbuilder.hal.annotation.HalLink;
import com.github.osvaldopina.linkbuilder.hal.annotation.HalLinks;
import com.github.osvaldopina.linkbuilder.hal.annotation.Variable;
import org.springframework.hateoas.ResourceSupport;

@HalLinks({
		@HalLink(controller = RootRestController.class, overridedRel = "self" , rel = "second",
				variables = {@Variable(name = "name", value = "#resource.name")})
})
public class SecondEmbedded extends ResourceSupport {

	private String name;

	public SecondEmbedded() {
	}

	public SecondEmbedded(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		if (!super.equals(o)) return false;

		SecondEmbedded that = (SecondEmbedded) o;

		return name != null ? name.equals(that.name) : that.name == null;

	}

	@Override
	public int hashCode() {
		int result = super.hashCode();
		result = 31 * result + (name != null ? name.hashCode() : 0);
		return result;
	}
}
