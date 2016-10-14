package com.github.osvaldopina.linkbuilder.example.todo;

import com.github.osvaldopina.linkbuilder.LinksBuilder;
import com.github.osvaldopina.linkbuilder.LinksBuilderFactory;
import com.github.osvaldopina.linkbuilder.annotation.EnableSelfFromCurrentCall;
import com.github.osvaldopina.linkbuilder.annotation.LinkTarget;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
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
    @EnableSelfFromCurrentCall
    public ResourceSupport root() {

        Payload payload = new Payload();

        LinksBuilder linksBuilder = linksBuilderFactory.create();

        linksBuilder.link().withSelfRel().fromCurrentCall();

        linksBuilder.link()
                .setExpressionPayload(payload)
                .withRel("a-link-with-expression-payload-check")
                .when("#payload.awaysTrueProperty")
                .fromControllerCall(RootRestController.class)
                .root();

        linksBuilder.link()
                .withRel("no-query-parameter")
                .fromControllerCall(RootRestController.class)
                .methodWithoutQueryParameter("value", null);

        linksBuilder.link()
                .withRel("user-defined-type-for-authenticated")
                .when("isAuthenticated()")
                .fromControllerCall(RootRestController.class)
                .queryParameterForUserDefinedType(new UserDefinedType("v1", "v2"));

        linksBuilder.link()
                .withRel("using-bean-in-expression-always-true")
                .when("@anyBean.isAlwaysTrue()")
                .fromControllerCall(RootRestController.class)
                .queryParameterForUserDefinedType(new UserDefinedType("v1", "v2"));

        linksBuilder.link()
                .withRel("using-bean-in-expression-always-false")
                .when("@anyBean.isAlwaysFalse()")
                .fromControllerCall(RootRestController.class)
                .queryParameterForUserDefinedType(new UserDefinedType("v1", "v2"));


        linksBuilder.link()
                .withRel("user-defined-type-for-non-authenticated")
                .when("not isAuthenticated()")
                .fromControllerCall(RootRestController.class)
                .queryParameterForUserDefinedType(new UserDefinedType("v1", "v2"));

        payload.add(linksBuilder.buildAll(Link.class));

        return payload;
    }

    @RequestMapping("/no-query-parameter/{id}")
    @EnableSelfFromCurrentCall
    @LinkTarget("a-method")
    public void methodWithoutQueryParameter(@PathVariable("id") String id, @RequestBody String payload) {

    }

    @RequestMapping("/user-defined-type")
    @EnableSelfFromCurrentCall
    public void queryParameterForUserDefinedType(UserDefinedType userDefinedType) {

    }


  }

