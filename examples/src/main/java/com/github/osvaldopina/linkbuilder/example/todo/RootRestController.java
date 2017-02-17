package com.github.osvaldopina.linkbuilder.example.todo;

import com.github.osvaldopina.linkbuilder.LinksBuilder;
import com.github.osvaldopina.linkbuilder.LinksBuilderFactory;
import com.github.osvaldopina.linkbuilder.annotation.SelfFromCurrentCall;
import com.github.osvaldopina.linkbuilder.annotation.GenerateUriTemplateFor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RootRestController {

    @Autowired
    private LinksBuilderFactory linksBuilderFactory;

    @RequestMapping("/")
    @SelfFromCurrentCall
    public ResourceSupport root() {

        Resource resource = new Resource();

        LinksBuilder linksBuilder = linksBuilderFactory.create(resource);

  //      linksBuilder.link().withSelfRel().fromCurrentCall();

        linksBuilder.link()
                .withRel("a-link-with-expression-resource-check")
                .fromControllerCall(RootRestController.class)
                .root();

        linksBuilder.link()
           //     .withRel("no-query-parameter")
                .fromControllerCall(RootRestController.class)
                .methodWithoutQueryParameter("value", null);

        linksBuilder.link()
                .withRel("user-defined-type-for-authenticated")
                .fromControllerCall(RootRestController.class)
                .queryParameterForUserDefinedType();

        linksBuilder.link()
                .withRel("using-bean-in-expression-always-true")
                .when("@anyBean.isAlwaysTrue()")
                .fromControllerCall(RootRestController.class)
                .queryParameterForUserDefinedType();

        linksBuilder.link()
                .withRel("using-bean-in-expression-always-false")
//                .when("@anyBean.isAlwaysFalse() && isAutenticated()")
//                .when("isAutenticated()")
                .fromControllerCall(RootRestController.class)
                .queryParameterForUserDefinedType();


        linksBuilder.link()
                .withRel("user-defined-type-for-non-authenticated")
                .fromControllerCall(RootRestController.class)
                .queryParameterForUserDefinedType();

        linksBuilder.buildAndSetAll();

        return resource;
    }

    @RequestMapping("/no-query-parameter/{id}")
    @SelfFromCurrentCall
    @GenerateUriTemplateFor(rel = "rel")
    public void methodWithoutQueryParameter(@PathVariable("id") String id, @RequestBody String payload) {

    }

    @RequestMapping("/user-defined-type")
    @SelfFromCurrentCall
    public void queryParameterForUserDefinedType() {

    }


}

