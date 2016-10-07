package com.github.osvaldopina.linkbuilder.example.extensions.expressionexecutor;

import com.github.osvaldopina.linkbuilder.LinksBuilder;
import com.github.osvaldopina.linkbuilder.LinksBuilderFactory;
import com.github.osvaldopina.linkbuilder.annotation.EnableSelfFromCurrentCall;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RootRestController {

    @Autowired
    private LinksBuilderFactory<Link> linksBuilderFactory;

    @RequestMapping("/")
    @EnableSelfFromCurrentCall
    public ResourceSupport root() {

        ResourceSupport payload = new ResourceSupport();

        LinksBuilder<Link> linksBuilder = linksBuilderFactory.create(payload);

        linksBuilder.link()
                .when("the expressions will always return true!!!")
                .withRel("rel")
                .fromControllerCall(RootRestController.class)
                .root();

        payload.add(linksBuilder.buildAll());

        return payload;
    }

}

