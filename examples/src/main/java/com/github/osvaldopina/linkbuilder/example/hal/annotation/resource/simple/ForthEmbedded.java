package com.github.osvaldopina.linkbuilder.example.hal.annotation.resource.simple;

import com.github.osvaldopina.linkbuilder.hal.annotation.HalLink;
import com.github.osvaldopina.linkbuilder.hal.annotation.HalLinks;
import com.github.osvaldopina.linkbuilder.hal.annotation.Param;
import org.springframework.hateoas.ResourceSupport;

@HalLinks({
        @HalLink(controller = RootRestController.class, overridedRel = "self" , rel = "third",
                params = {@Param(name = "name", value = "#resource.key")})
})
public class ForthEmbedded extends ResourceSupport {

    public ForthEmbedded() {

    }

    public ForthEmbedded(int key) {
        this.key = key;
    }

    private int key;

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        ForthEmbedded that = (ForthEmbedded) o;

        return key == that.key;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + key;
        return result;
    }
}
