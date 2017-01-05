package com.github.osvaldopina.linkbuilder.example.hal.annotation.resource.simple;

import java.util.Arrays;

import com.github.osvaldopina.linkbuilder.LinksBuilderFactory;
import com.github.osvaldopina.linkbuilder.annotation.GenerateUriTemplateFor;
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
    @GenerateUriTemplateFor(rel = "root")
    public Payload root() {
        Payload payload = new Payload();

        payload.setQueryValue("anyQueryValue");
        payload.setPathValue("anyPathValue");

        FirstTypeEmbeddedPayload firstType = new FirstTypeEmbeddedPayload(1);
        SecondTypeEmbeddedPayload secondType1 = new SecondTypeEmbeddedPayload("name");
        SecondTypeEmbeddedPayload secondType2 = new SecondTypeEmbeddedPayload("other-name");

        payload.addEmbedded("first", firstType);
        payload.addEmbedded("second", Arrays.asList(secondType1, secondType2));

        return payload;
    }

    @RequestMapping("/first-embedded/{id}")
    @GenerateUriTemplateFor(rel = "first")
    public FirstTypeEmbeddedPayload getFirstEmbedded(@PathVariable("id") int id) {
        return null;
    }

    @RequestMapping("/second-embedded/{name}")
    @GenerateUriTemplateFor(rel = "second")
    public SecondTypeEmbeddedPayload getSecondEmbedded(@PathVariable("name") int id) {
        return null;
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

