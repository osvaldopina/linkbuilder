package com.github.osvaldopina.linkbuilder.example.builder.templated;

import com.github.osvaldopina.linkbuilder.LinksBuilder;
import com.github.osvaldopina.linkbuilder.LinksBuilderFactory;
import com.github.osvaldopina.linkbuilder.annotation.SelfFromCurrentCall;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RootRestController {

    @Autowired
    private LinksBuilderFactory linksBuilderFactory;

    @RequestMapping("/")
    @SelfFromCurrentCall
    public ResourceSupport root() {

        ResourceSupport resource = new ResourceSupport();

        LinksBuilder linksBuilder = linksBuilderFactory.create(resource);

        linksBuilder.link()
                .withRel("templated-first-rel")
                .templated()
                .dontSubstituteNullValues()
                .fromControllerCall(ResourcesRestController.class)
                .oneResource("path",null);

        linksBuilder.link()
                .withRel("templated-second-rel")
                .templated()
                .dontSubstituteNullValues()
                .fromControllerCall(ResourcesRestController.class)
                .otherResource("path", null);

        linksBuilder.buildAndSetAll();

        return resource;
    }

 }

