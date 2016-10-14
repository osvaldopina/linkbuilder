package com.github.osvaldopina.linkbuilder.example.hal;

import com.github.osvaldopina.linkbuilder.LinksBuilder;
import com.github.osvaldopina.linkbuilder.LinksBuilderFactory;
import com.github.osvaldopina.linkbuilder.annotation.EnableSelfFromCurrentCall;
import com.github.osvaldopina.linkbuilder.hal.HalLink;
import com.github.osvaldopina.linkbuilder.hal.HalLinkBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RootRestController {

    @Autowired
    private LinksBuilderFactory<HalLink> linksBuilderFactory;

    @RequestMapping("/")
    @EnableSelfFromCurrentCall
    public ResourceSupport root(@RequestParam(value = "query", required = false) String query) {

        /*
        ResourceSupport payload = new ResourceSupport();

        LinkBuilder<HalLink> linksBuilder = linksBuilderFactory.create(payload);

        linksBuilder.link()
                .withRel("rel")
                .ti
                .fromControllerCall(RootRestController.class)
                .root("query-value");

        payload.add(linksBuilder.buildAll());

        return payload;
        */
        return null;
    }

}

