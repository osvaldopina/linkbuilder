package com.github.osvaldopina.linkbuilder.example.directlink.responseentity;

import com.github.osvaldopina.linkbuilder.LinksBuilderFactory;
import com.github.osvaldopina.linkbuilder.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    @Links({
            @Link(destination = RootRestController.class, rel = "direct-link", params = {
                    @Param(name = "query", value = "#payload.queryValue"),
                    @Param(name = "path", value = "#payload.pathValue")
            }),
            @Link(destination = RootRestController.class, rel = "direct-link", overrideRel = "direct-link-overrided",
                    params = {
                            @Param(name = "query", value = "#payload.queryValue"),
                            @Param(name = "path", value = "#payload.pathValue")
                    }),
            @Link(destination = RootRestController.class,
                    rel = "direct-link-templated",
                    templated = true,
                    params = {
                            @Param(name = "templated", value = "'templated-value'")
                    })
    })
    public ResponseEntity<Payload> root() {
        Payload payload = new Payload();

        payload.setQueryValue("anyQueryValue");
        payload.setPathValue("anyPathValue");

        return new ResponseEntity<Payload>(payload, HttpStatus.OK);
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

