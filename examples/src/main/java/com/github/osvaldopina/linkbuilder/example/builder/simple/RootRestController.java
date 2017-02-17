package com.github.osvaldopina.linkbuilder.example.builder.simple;

import com.github.osvaldopina.linkbuilder.LinksBuilder;
import com.github.osvaldopina.linkbuilder.LinksBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RootRestController {

    @Autowired
    private LinksBuilderFactory linksBuilderFactory;

    @RequestMapping("/")
    public ResourceSupport root() {

        ResourceSupport resource = new ResourceSupport();

        LinksBuilder linksBuilder = linksBuilderFactory.create(resource);

        linksBuilder.link()
                .withRel("first-rel")
                .fromControllerCall(ResourcesRestController.class)
                .oneResource("path", "query");

        linksBuilder.link()
                .withRel("second-rel")
                .fromControllerCall(ResourcesRestController.class)
                .otherResource("path", "query");

        linksBuilder.buildAndSetAll();

        return resource;
    }

 }

