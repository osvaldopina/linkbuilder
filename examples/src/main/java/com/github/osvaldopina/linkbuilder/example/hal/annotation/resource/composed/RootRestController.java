package com.github.osvaldopina.linkbuilder.example.hal.annotation.resource.composed;

import com.github.osvaldopina.linkbuilder.LinksBuilderFactory;
import com.github.osvaldopina.linkbuilder.annotation.EnableSelfFromCurrentCall;
import com.github.osvaldopina.linkbuilder.example.hal.annotation.resource.composed.link.LINK_DESTINATION;
import com.github.osvaldopina.linkbuilder.example.hal.annotation.resource.composed.link.MyGenerateUriTemplateFor;
import com.github.osvaldopina.linkbuilder.example.hal.annotation.resource.composed.link.MyHalLink;
import com.github.osvaldopina.linkbuilder.example.hal.annotation.resource.composed.link.MyHalLinks;
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
    @MyHalLinks({
            @MyHalLink(destination = LINK_DESTINATION.DIRECT_LINK,
                    hreflang = "href-lang-1" ,
                    params = {
                    @Param(name = "query", value = "#resource.queryValue"),
                    @Param(name = "path", value = "#resource.pathValue")
            }),
            @MyHalLink(destination = LINK_DESTINATION.DIRECT_LINK,
                    hreflang = "href-lang-2",
                    overridedRel = "direct-link-overrided",
                    params = {
                            @Param(name = "query", value = "#resource.queryValue"),
                            @Param(name = "path", value = "#resource.pathValue")
                    }),
            @MyHalLink(destination = LINK_DESTINATION.DIRECT_LINK_TEMPLATED,
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
    @MyGenerateUriTemplateFor(destination = LINK_DESTINATION.DIRECT_LINK)
    public void directLink(@RequestParam(value = "query", required = false) String query,
                           @PathVariable("path") String path) {

    }

    @RequestMapping("/direct-link/templated")
    @MyGenerateUriTemplateFor(destination = LINK_DESTINATION.DIRECT_LINK_TEMPLATED)
    public void directLinkTemplated(
            @RequestParam(value = "non_templated", required = false) String nonTemplated,
            @RequestParam(value = "templated", required = false) String templated) {
    }
}

