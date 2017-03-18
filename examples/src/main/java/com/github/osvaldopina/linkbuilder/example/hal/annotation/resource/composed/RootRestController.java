package com.github.osvaldopina.linkbuilder.example.hal.annotation.resource.composed;

import com.github.osvaldopina.linkbuilder.example.hal.annotation.resource.composed.link.Destination;
import com.github.osvaldopina.linkbuilder.example.hal.annotation.resource.composed.link.MyLinkDestination;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RootRestController {

    @RequestMapping("/")
    public Resource root() {
        Resource resource = new Resource();

        resource.setQueryValue("anyQueryValue");
        resource.setPathValue("anyPathValue");

        return resource;
    }

    @RequestMapping("/direct-link/{path}")
    @MyLinkDestination(destination = Destination.DIRECT_LINK)
    public void directLink(@RequestParam(value = "query", required = false) String query,
                           @PathVariable("path") String path) {

    }

    @RequestMapping("/direct-link/templated")
    @MyLinkDestination(destination = Destination.DIRECT_LINK_TEMPLATED)
    public void directLinkTemplated(
            @RequestParam(value = "non_templated", required = false) String nonTemplated,
            @RequestParam(value = "templated", required = false) String templated) {
    }
}

