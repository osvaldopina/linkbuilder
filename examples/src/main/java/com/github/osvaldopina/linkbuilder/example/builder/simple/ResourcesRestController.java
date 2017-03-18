package com.github.osvaldopina.linkbuilder.example.builder.simple;

import com.github.osvaldopina.linkbuilder.annotation.LinkDestination;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ResourcesRestController {

    @RequestMapping("/resource1/{path_param}")
    @LinkDestination
    public ResourceSupport oneResource(
            @PathVariable("path_param") String pathParam,@RequestParam("query_param") String queryParam) {
        return null;
    }

    @RequestMapping("/resource2/{path_param}")
    @LinkDestination
    public ResourceSupport otherResource(
            @PathVariable("path_param") String pathParam,@RequestParam("query_param") String queryParam) {
        return null;
    }
}

