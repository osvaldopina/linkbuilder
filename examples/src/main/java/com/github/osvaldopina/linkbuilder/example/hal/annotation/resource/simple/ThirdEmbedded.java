package com.github.osvaldopina.linkbuilder.example.hal.annotation.resource.simple;

import com.github.osvaldopina.linkbuilder.hal.annotation.HalLink;
import com.github.osvaldopina.linkbuilder.hal.annotation.HalLinks;
import com.github.osvaldopina.linkbuilder.hal.annotation.Param;

import java.util.ArrayList;
import java.util.List;

@HalLinks({
        @HalLink(controller = RootRestController.class, overridedRel = "self" , rel = "third",
                params = {@Param(name = "name", value = "#resource.name")})
})
public class ThirdEmbedded {

    public ThirdEmbedded() {

    }

    public ThirdEmbedded(String name) {
        this.name = name;
    }

    private List<ForthEmbedded> forthEmbeddeds = new ArrayList<ForthEmbedded>();

    private FirstEmbedded firstEmbedded;

    private String name;

    public List<ForthEmbedded> getForthEmbeddeds() {
        return forthEmbeddeds;
    }

    public void setForthEmbeddeds(List<ForthEmbedded> forthEmbeddeds) {
        this.forthEmbeddeds = forthEmbeddeds;
    }

    public FirstEmbedded getFirstEmbedded() {
        return firstEmbedded;
    }

    public void setFirstEmbedded(FirstEmbedded firstEmbedded) {
        this.firstEmbedded = firstEmbedded;
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

        ThirdEmbedded that = (ThirdEmbedded) o;

        return name != null ? name.equals(that.name) : that.name == null;

    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}
