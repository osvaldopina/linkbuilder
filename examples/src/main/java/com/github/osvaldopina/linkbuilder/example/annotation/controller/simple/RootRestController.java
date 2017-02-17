package com.github.osvaldopina.linkbuilder.example.annotation.controller.simple;

import com.github.osvaldopina.linkbuilder.LinksBuilderFactory;
import com.github.osvaldopina.linkbuilder.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RootRestController {

    @RequestMapping("/")
    @SelfFromCurrentCall
    @Links({
            @Link(controller = RootRestController.class, rel = "direct-link", variables = {
                    @Variable(name = "query", value = "#resource.queryValue"),
                    @Variable(name = "path", value = "#resource.pathValue")
            }),
            @Link(controller = RootRestController.class, rel = "direct-link", overridedRel = "direct-link-overrided",
                    variables = {
                            @Variable(name = "query", value = "#resource.queryValue"),
                            @Variable(name = "path", value = "#resource.pathValue")
                    }),
            @Link(controller = RootRestController.class,
                    rel = "direct-link-templated",
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

