package com.github.osvaldopina.linkbuilder.example.extensions.templategenerator;

import com.github.osvaldopina.linkbuilder.LinksBuilder;
import com.github.osvaldopina.linkbuilder.LinksBuilderFactory;
import com.github.osvaldopina.linkbuilder.annotation.EnableSelfFromCurrentCall;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RootRestController {

    @Autowired
    private LinksBuilderFactory linksBuilderFactory;

    @RequestMapping("/")
    @EnableSelfFromCurrentCall
    public ResourceSupport root(@RequestParam(value = "query", required = false) String query) {

        ResourceSupport payload = new ResourceSupport();

        LinksBuilder linksBuilder = linksBuilderFactory.create(payload);

        linksBuilder.link()
                .withRel("rel")
                .fromControllerCall(RootRestController.class)
                .root("query-value");

        payload.add(linksBuilder.buildAll(Link.class));

        return payload;
    }

}

