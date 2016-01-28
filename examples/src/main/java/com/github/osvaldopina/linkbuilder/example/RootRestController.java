package com.github.osvaldopina.linkbuilder.example;

import com.github.osvaldopina.linkbuilder.LinksBuilder;
import com.github.osvaldopina.linkbuilder.LinksBuilderFactory;
import com.github.osvaldopina.linkbuilder.annotation.EnableSelfFromCurrentCall;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.web.bind.annotation.*;

@RestController
public class RootRestController {

    @Autowired
    private LinksBuilderFactory linksBuilderFactory;

    @RequestMapping("/")
    @EnableSelfFromCurrentCall
    public ResourceSupport root(
            @RequestParam(value = "name", required = false, defaultValue = "World") String name) {

        ResourceSupport payload = new ResourceSupport();

        LinksBuilder linksBuilder = linksBuilderFactory.create();

        linksBuilder.link().withSelfRel().fromCurrentCall();
        linksBuilder.link().withRel("no-query-parameter").
                fromControllerCall(RootRestController.class).methodWithoutQueryParameter("value", null);

        linksBuilder.link().withRel("user-defined-type").
                fromControllerCall(RootRestController.class).queryParameterForUserDefinedType(new UserDefinedType("v1", "v2"));

        payload.add(linksBuilder.buildAll());

        return payload;
    }

    @RequestMapping("/no-query-parameter/{id}")
    @EnableSelfFromCurrentCall
    public void methodWithoutQueryParameter(@PathVariable("id") String id,@RequestBody String payload) {

    }

    @RequestMapping("/user-defined-type")
    @EnableSelfFromCurrentCall
    public void queryParameterForUserDefinedType(UserDefinedType userDefinedType) {

    }


}