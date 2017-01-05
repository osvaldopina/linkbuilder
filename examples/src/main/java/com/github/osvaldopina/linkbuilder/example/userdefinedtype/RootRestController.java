package com.github.osvaldopina.linkbuilder.example.userdefinedtype;

import com.github.osvaldopina.linkbuilder.LinkBuilder;
import com.github.osvaldopina.linkbuilder.LinksBuilder;
import com.github.osvaldopina.linkbuilder.LinksBuilderFactory;
import com.github.osvaldopina.linkbuilder.annotation.GenerateUriTemplateFor;
import com.github.osvaldopina.linkbuilder.annotation.Link;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RootRestController {

    @Autowired
    private LinksBuilderFactory linksBuilderFactory;

    @RequestMapping("/")
    @GenerateUriTemplateFor
    public ResourceSupport root() {

        ResourceSupport resource = new ResourceSupport();

        LinksBuilder linksBuilder = linksBuilderFactory.create(resource);

        LinkBuilder lb = linksBuilder.link();

        lb
                .withRel("rel")
                .fromControllerCall(RootRestController.class)
                .queryParameterForUserDefinedType(new UserDefinedType("v1", "v2"));

       resource.add((org.springframework.hateoas.Link) lb.build());

        return resource;
    }

    @RequestMapping("/user-defined-type")
    @GenerateUriTemplateFor
    public void queryParameterForUserDefinedType(UserDefinedType userDefinedType) {

    }

}

