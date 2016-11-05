package com.github.osvaldopina.linkbuilder.example.userdefinedtype;

import com.github.osvaldopina.linkbuilder.LinksBuilder;
import com.github.osvaldopina.linkbuilder.LinksBuilderFactory;
import com.github.osvaldopina.linkbuilder.annotation.GenerateUriTemplateFor;
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

        ResourceSupport payload = new ResourceSupport();

        LinksBuilder linksBuilder = linksBuilderFactory.create(payload);

        linksBuilder.link()
                .withRel("rel")
                .fromControllerCall(RootRestController.class)
                .queryParameterForUserDefinedType(new UserDefinedType("v1", "v2"));

        linksBuilder.buildAndSetAll();

        return payload;
    }

    @RequestMapping("/user-defined-type")
    @GenerateUriTemplateFor
    public void queryParameterForUserDefinedType(UserDefinedType userDefinedType) {

    }

}

