package com.github.osvaldopina.linkbuilder.example.hal.annotation.controller.simple;

import com.github.osvaldopina.linkbuilder.LinksBuilderFactory;
import com.github.osvaldopina.linkbuilder.annotation.EnableSelfFromCurrentCall;
import com.github.osvaldopina.linkbuilder.annotation.GenerateUriTemplateFor;
import com.github.osvaldopina.linkbuilder.hal.annotation.HalLink;
import com.github.osvaldopina.linkbuilder.hal.annotation.HalLinks;
import com.github.osvaldopina.linkbuilder.hal.annotation.Param;
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
    @EnableSelfFromCurrentCall
    @HalLinks({
            @HalLink(controller = RootRestController.class,
                    rel = "direct-link",
                    hreflang = "href-lang-1" ,
                    params = {
                    @Param(name = "query", value = "#resource.queryValue"),
                    @Param(name = "path", value = "#resource.pathValue")
            }),
            @HalLink(controller = RootRestController.class,
                    rel = "direct-link",
                    hreflang = "href-lang-2",
                    overridedRel = "direct-link-overrided",
                    params = {
                            @Param(name = "query", value = "#resource.queryValue"),
                            @Param(name = "path", value = "#resource.pathValue")
                    }),
            @HalLink(controller = RootRestController.class,
                    rel = "direct-link-templated",
                    hreflang = "href-lang-3",
                    templated = true,
                    params = {
                            @Param(name = "templated", value = "'templated-value'")
                    })
    })
    public Resource root() {
        Resource resource = new Resource();

        resource.setQueryValue("anyQueryValue");
        resource.setPathValue("anyPathValue");

        return resource;
    }

    @RequestMapping("/direct-link/{path}")
    @GenerateUriTemplateFor(rel = "direct-link")
    public void directLink(@RequestParam(value = "query", required = false) String query,
                           @PathVariable("path") String path) {

    }

    @RequestMapping("/direct-link/templated")
    @GenerateUriTemplateFor(rel = "direct-link-templated")
    public void directLinkTemplated(
            @RequestParam(value = "non_templated", required = false) String nonTemplated,
            @RequestParam(value = "templated", required = false) String templated) {
    }
}

