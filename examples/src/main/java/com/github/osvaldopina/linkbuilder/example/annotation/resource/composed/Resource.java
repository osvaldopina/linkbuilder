package com.github.osvaldopina.linkbuilder.example.annotation.resource.composed;

import com.github.osvaldopina.linkbuilder.annotation.Variable;
import com.github.osvaldopina.linkbuilder.example.annotation.resource.composed.link.Destionation;
import com.github.osvaldopina.linkbuilder.example.annotation.resource.composed.link.MyLink;
import com.github.osvaldopina.linkbuilder.example.annotation.resource.composed.link.MyLinks;
import org.springframework.hateoas.ResourceSupport;

@MyLinks(value = {
        @MyLink(destination = Destionation.ROOT, overridedRel = "self"),
        @MyLink(destination = Destionation.DIRECT_LINK, variables = {
                @Variable(name = "query", value = "#resource.queryValue"),
                @Variable(name = "path", value = "#resource.pathValue")
        }),
        @MyLink(destination = Destionation.DIRECT_LINK, overridedRel = "direct-link-overrided",
                variables = {
                        @Variable(name = "query", value = "#resource.queryValue"),
                        @Variable(name = "path", value = "#resource.pathValue")
                }),
        @MyLink(destination = Destionation.DIRECT_LINK_TEMPLATED,
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
