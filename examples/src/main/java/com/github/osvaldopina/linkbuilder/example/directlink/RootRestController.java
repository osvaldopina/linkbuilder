package com.github.osvaldopina.linkbuilder.example.directlink;

import com.github.osvaldopina.linkbuilder.LinksBuilder;
import com.github.osvaldopina.linkbuilder.LinksBuilderFactory;
import com.github.osvaldopina.linkbuilder.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ResourceSupport;
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
            @Link(destination = RootRestController.class, target = "direct", relation = "direct", params = {
                    @Param(name = "query",value = "#payload.queryValue"),
                    @Param(name = "path",value = "#payload.pathValue")
            })
    })
    public Payload root() {
        Payload payload = new Payload();

        payload.setQueryValue("anyQueryValue");
        payload.setPathValue("anyPathValue");

        return payload;
     }

    @RequestMapping("/direct-link/{path}")
    @LinkTarget("direct")
    public void directLink(@RequestParam(value = "query", required = false) String query,
                           @PathVariable("path") String path) {

    }

}

