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

    @RequestMapping("/")
    @GenerateUriTemplateFor(rel = "root")
    public Resource root() {
        Resource resource = new Resource();

        resource.setQueryValue("anyQueryValue");
        resource.setPathValue("anyPathValue");

        FirstEmbedded firstEmbedded = new FirstEmbedded(1);
        SecondEmbedded secondEmbedded1 = new SecondEmbedded("name");
        SecondEmbedded secondEmbedded2 = new SecondEmbedded("other-name");

        resource.addEmbedded("first", firstEmbedded);
        resource.addEmbedded("second", Arrays.asList(secondEmbedded1, secondEmbedded2));

        ForthEmbedded forthEmbedded = new ForthEmbedded(2);

        ThirdEmbedded thirdEmbedded = new ThirdEmbedded("name-forth");
        thirdEmbedded.getForthEmbeddeds().add(forthEmbedded);
        thirdEmbedded.setFirstEmbedded(new FirstEmbedded(5));


        firstEmbedded.setThirdEmbedded(thirdEmbedded);

        return resource;
    }

    @RequestMapping("/first-embedded/{id}")
    @GenerateUriTemplateFor(rel = "first")
    public FirstEmbedded getFirstEmbedded(@PathVariable("id") int id) {
        return null;
    }

    @RequestMapping("/second-embedded/{name}")
    @GenerateUriTemplateFor(rel = "second")
    public SecondEmbedded getSecondEmbedded(@PathVariable("name") int name) {
        return null;
    }

    @RequestMapping("/third-embedded/{name}")
    @GenerateUriTemplateFor(rel = "third")
    public SecondEmbedded getThirdEmbedded(@PathVariable("name") int name) {
        return null;
    }

    @RequestMapping("/forth-embedded/{key}")
    @GenerateUriTemplateFor(rel = "forth")
    public SecondEmbedded getForthEmbedded(@PathVariable("key") int key) {
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

