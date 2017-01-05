package com.github.osvaldopina.linkbuilder.example.commom.annotatedlinkrel;

import com.github.osvaldopina.linkbuilder.LinksBuilder;
import com.github.osvaldopina.linkbuilder.LinksBuilderFactory;
import com.github.osvaldopina.linkbuilder.annotation.EnableSelfFromCurrentCall;
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
    @EnableSelfFromCurrentCall
    public ResourceSupport root() {

        ResourceSupport resource = new ResourceSupport();

        LinksBuilder linksBuilder = linksBuilderFactory.create(resource);

        linksBuilder.link()
                .withRel("non-annotated-rel")
                .fromControllerCall(RootRestController.class)
                .resource1();

        linksBuilder.link()
                .withRel("annotated-rel-but-overrided")
                .fromControllerCall(RootRestController.class)
                .resource2();

        linksBuilder.link()
                .fromControllerCall(RootRestController.class)
                .resource2();

        linksBuilder.buildAndSetAll();

        return resource;
    }

    @RequestMapping("/resource1")
    @GenerateUriTemplateFor
    public ResourceSupport resource1() {
        return null;
    }

    @RequestMapping("/resource2")
    @GenerateUriTemplateFor(rel = "annotated-rel")
    public ResourceSupport resource2() {
        return null;
    }
}

