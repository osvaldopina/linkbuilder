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
    public ResourceSupport root() {

        Payload payload = new Payload();

        LinksBuilder  linksBuilder = linksBuilderFactory.create();

        linksBuilder.link().withSelfRel().fromCurrentCall();

        linksBuilder.link()
                .setExpressionPayload(payload)
                .withRel("a-link-with-spel-payload-check")
                .when("#payload.awaysTrueProperty")
                .fromControllerCall(RootRestController.class)
                .root();


        linksBuilder.link()
                .withRel("no-query-parameter")
                .fromControllerCall(RootRestController.class)
                .methodWithoutQueryParameter("value", null);

        linksBuilder.link()
                .withRel("user-defined-type-for-authenticated")
                .when("isAuthenticated()" )
                .fromControllerCall(RootRestController.class)
                .queryParameterForUserDefinedType(new UserDefinedType("v1", "v2"));

        linksBuilder.link()
                .withRel("using-bean-in-expression-always-true")
                .when("@anyBean.isAlwaysTrue()" )
                .fromControllerCall(RootRestController.class)
                .queryParameterForUserDefinedType(new UserDefinedType("v1", "v2"));

        linksBuilder.link()
                .withRel("using-bean-in-expression-always-false")
                .when("@anyBean.isAlwaysFalse()" )
                .fromControllerCall(RootRestController.class)
                .queryParameterForUserDefinedType(new UserDefinedType("v1", "v2"));


        linksBuilder.link()
                .withRel("user-defined-type-for-non-authenticated")
                .when("not isAuthenticated()")
                .fromControllerCall(RootRestController.class)
                .queryParameterForUserDefinedType(new UserDefinedType("v1", "v2"));

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