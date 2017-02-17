package com.github.osvaldopina.linkbuilder.example.annotation.controller.composed;

import com.github.osvaldopina.linkbuilder.LinksBuilderFactory;
import com.github.osvaldopina.linkbuilder.annotation.SelfFromCurrentCall;
import com.github.osvaldopina.linkbuilder.annotation.Variable;
import com.github.osvaldopina.linkbuilder.example.annotation.controller.composed.link.MyGenerateUriTemplateFor;
import com.github.osvaldopina.linkbuilder.example.annotation.controller.composed.link.Destination;
import com.github.osvaldopina.linkbuilder.example.annotation.controller.composed.link.MyLink;
import com.github.osvaldopina.linkbuilder.example.annotation.controller.composed.link.MyLinks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RootRestController {

	@Autowired
	private LinksBuilderFactory linksBuilderFactory;

    @RequestMapping("/")
    @SelfFromCurrentCall
    @MyLinks({
            @MyLink(destination = Destination.DIRECT_LINK, variables = {
                    @Variable(name = "query", value = "#resource.queryValue"),
                    @Variable(name = "path", value = "#resource.pathValue")
            }),
            @MyLink(destination = Destination.DIRECT_LINK, overridedRel = "direct-link-overrided",
                    variables = {
                            @Variable(name = "query", value = "#resource.queryValue"),
                            @Variable(name = "path", value = "#resource.pathValue")
                    }),
            @MyLink(destination = Destination.DIRECT_LINK_TEMPLATED,
                    templated = true,
                    variables = {
                            @Variable(name = "templated", value = "'templated-value'")
             })
    })
    public Resource root() {
        Resource resource = new Resource();

        resource.setQueryValue("anyQueryValue");
        resource.setPathValue("anyPathValue");

        return resource;
    }

    @RequestMapping("/direct-link/{path}")
    @MyGenerateUriTemplateFor(destination = Destination.DIRECT_LINK)
    public void directLink(@RequestParam(value = "query", required = false) String query,
                           @PathVariable("path") String path) {

    }

    @RequestMapping("/direct-link/templated")
    @MyGenerateUriTemplateFor(destination = Destination.DIRECT_LINK_TEMPLATED)
    public void directLinkTemplated(
            @RequestParam(value = "non_templated", required = false) String nonTemplated,
            @RequestParam(value = "templated", required = false) String templated) {
    }
}

